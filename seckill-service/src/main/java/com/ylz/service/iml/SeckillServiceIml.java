package com.ylz.service.iml;

import com.ylz.dao.SeckillMapper;
import com.ylz.dao.SuccessKilledMapper;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.entity.Seckill;
import com.ylz.entity.SuccessKilled;
import com.ylz.entity.SuccessKilledKey;
import com.ylz.enums.SeckillEnums;
import com.ylz.exception.*;
import com.ylz.service.SeckillService;
import com.ylz.service.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by liuburu on 2017/2/18.
 */
@Service
public class SeckillServiceIml implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    SuccessKilledMapper successKilledMapper;

    public int selectTotalCount() {
        return seckillMapper.selectTotalCount();
    }

    public Seckill selectOneSeckill(int seckillId) {
        Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        return seckill;
    }

    public List<Seckill> selectSeckillByPage(int pageNo, int pageSize) {
        int begin = (pageNo - 1) * pageSize;
        return seckillMapper.selectByPage(begin, pageSize);
    }

    public ExposerResult acquireSeckillURL(int seckillId)
            throws NoSuchSeckillException, StoreEmptyException, SeckillNoStartException {
        Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        if (seckill == null) {//没有该商品
            throw new NoSuchSeckillException(SeckillEnums.SECKILL_NO_SUCH.getStateInfo());
        }
        Date now = new Date();
        if (now.after(seckill.getEndTime())) {//该产品秒杀时间已经结束!
            throw new SeckillEndException(SeckillEnums.SECKILL_AREADY_CLOSE.getStateInfo());
        } else if (now.before(seckill.getStartTime())) {//该商品秒杀活动还未开始!
            throw new SeckillNoStartException(SeckillEnums.SECKILL_NO_START.getStateInfo());
        } else {
            Seckill seckill1 = seckillMapper.selectByPrimaryKey(seckillId);
            String md5URL = MD5Utils.toMD5Code(seckillId);
            ExposerResult result = new ExposerResult(true, seckillId, md5URL, seckill1);
            return result;
        }
    }


    /**
     * 执行秒杀
     * 1.记录秒杀详情明细（问题：重复秒杀）
     * 2.减少库存（问题：库存不足）
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    @Transactional
    public ExecuteSeckillResult excuteSeckill(int seckillId, long userPhone, String md5)
            throws RepeatSeckillException, StoreEmptyException {
        SuccessKilled record = new SuccessKilled();
        record.setSeckillId(seckillId);
        record.setUserPhone(userPhone);
        if (!MD5Utils.toMD5Code(seckillId).equals(md5)) {//秒杀地址被篡改
            throw new URLRewriteException(SeckillEnums.SECKILL_URL_REWRITE.getStateInfo());
        }
        int insertRow = successKilledMapper.insertSelective(record);
        //1.登记秒杀详情
        if (insertRow == 0) {//重复秒杀！
            throw new RepeatSeckillException(SeckillEnums.SECKILL_REPEAT.getStateInfo());
        } else {//登记成功
            //2.减少库存
            int updateRow = seckillMapper.updateSeckillNumber(seckillId);
            if (updateRow == 0) {//库存已经被秒杀光了!
                throw new StoreEmptyException(SeckillEnums.SECKILL_STORE_EMPTY.getStateInfo());
            } else {//减少库存成功
                SuccessKilledKey killedKey = new SuccessKilledKey(seckillId, userPhone);
                SuccessKilled successKilled = successKilledMapper.selectByPrimaryKey(killedKey);
                ExecuteSeckillResult result = new ExecuteSeckillResult(true, successKilled);
                return result;
            }
        }
    }
}
