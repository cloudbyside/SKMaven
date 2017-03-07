package com.ylz.service.iml;

import com.ylz.base.SeckillException;
import com.ylz.dao.ProcedureMapper;
import com.ylz.dao.SeckillMapper;
import com.ylz.dao.SuccessKilledMapper;
import com.ylz.dto.ExecuteSeckillResult;
import com.ylz.dto.ExposerResult;
import com.ylz.dto.UserSucessKillsDTO;
import com.ylz.entity.Seckill;
import com.ylz.entity.SuccessKilled;
import com.ylz.entity.SuccessKilledKey;
import com.ylz.enums.AttributeEnums;
import com.ylz.enums.QueryTypeEnum;
import com.ylz.enums.SeckillEnums;
import com.ylz.exception.*;
import com.ylz.redis.RedisDao;
import com.ylz.redis.proxy.RedisSeckillProxy;
import com.ylz.service.SeckillService;
import com.ylz.service.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuburu on 2017/2/18.
 */
@Service
public class SeckillServiceIml implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    SuccessKilledMapper successKilledMapper;

    @Autowired
    ProcedureMapper procedureMapper;/*存储过程接口*/

    @Autowired
    RedisDao redisDao;/*redis通用操作类，需要配合proxy代理一起使用*/


    @Override
    public Map<String,Integer> selectAllCount() {
        Map<String,Integer> totals = new HashMap<String,Integer>();
        totals.put(QueryTypeEnum.TYPE_0.getQueryType(),selectTotalCount(0));
        totals.put(QueryTypeEnum.TYPE_1.getQueryType(),selectTotalCount(1));
        totals.put(QueryTypeEnum.TYPE_2.getQueryType(),selectTotalCount(2));
        totals.put(QueryTypeEnum.TYPE_3.getQueryType(),selectTotalCount(3));
        return totals;
    }

    public int selectTotalCount(int sort) {
        return seckillMapper.selectTotalCount(sort);
    }

    public Seckill selectOneSeckill(int seckillId) {
        Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        return seckill;
    }

    public List<Seckill> selectSeckillByPage(int pageNo, int pageSize,int sort,String order) {
        int begin = (pageNo - 1) * pageSize;
        return seckillMapper.selectByPage(begin, pageSize,sort,order);
    }

    public ExposerResult acquireSeckillURL(int seckillId)
            throws NoSuchSeckillException, StoreEmptyException{
        /*1.直接从数据库中查找数据*/
      //  Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        /*
        * 2.此处可以进行redis优化
        *       如果redis中存在数据，那么就从redis中获取；
        *       如果redis中没有数据，那么就从数据中查找数据，并且把数据放到redis缓存中
        * */
        RedisSeckillProxy seckillProxy = new RedisSeckillProxy(redisDao);
        Seckill seckill = seckillProxy.getSeckill(seckillId);
        if(seckill==null){
            seckill = seckillMapper.selectByPrimaryKey(seckillId);//从数据库中获取
            if (seckill == null) {//数据中也没有该商品
                throw new NoSuchSeckillException(SeckillEnums.SECKILL_NO_SUCH.getStateInfo());
            }
            seckillProxy.saveObject(seckill);//保存到redis缓存中
        }

        Date now = new Date();
        if (now.after(seckill.getEndTime())) {//该产品秒杀时间已经结束!
            throw new SeckillEndException(SeckillEnums.SECKILL_AREADY_CLOSE.getStateInfo());
        }  else {
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

    @Override
    public List<UserSucessKillsDTO> queryUserSeckResult(Long userPhone,int pageNo,int pageSize) {
        int begin = (pageNo-1)*pageSize;
        return successKilledMapper.queryUserResultsByPage(userPhone,begin,pageSize);
    }

    @Override
    public int queryUserSeckResultCount(Long userPhone) {
        return successKilledMapper.queryUserResultsCount(userPhone);
    }

    @Override
    public ExecuteSeckillResult excuteSeckillByPro(int seckillId,Long userPhone,  String md5)
    throws RepeatSeckillException,StoreEmptyException,SeckillException,URLRewriteException,Exception{
        if (!MD5Utils.toMD5Code(seckillId).equals(md5)) {//秒杀地址被篡改
            throw new URLRewriteException(SeckillEnums.SECKILL_URL_REWRITE.getStateInfo());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(AttributeEnums.USER_PHONE.getSkAttr(),userPhone);
        map.put(AttributeEnums.SECKILL_ID.getSkAttr(),seckillId);
        map.put(AttributeEnums.CREATE_TIME.getSkAttr(),new Date());
        map.put(AttributeEnums.RESULT.getSkAttr(),-999);
        procedureMapper.excuteSeckillPro(map);
        long result = (long)map.get(AttributeEnums.RESULT.getSkAttr());
        if(result==-3){
            throw new StoreEmptyException(SeckillEnums.SECKILL_STORE_EMPTY.getStateInfo());
        }else if(result==-2){
            throw new Exception(SeckillEnums.SECKILL_DATABASE_ERROR.getStateInfo());
        }else if(result==-1){
            throw new RepeatSeckillException(SeckillEnums.SECKILL_REPEAT.getStateInfo());
        }else if(result==1){
                SuccessKilledKey killedKey = new SuccessKilledKey(seckillId, userPhone);
                SuccessKilled successKilled = successKilledMapper.selectByPrimaryKey(killedKey);
                ExecuteSeckillResult seckillResult = new ExecuteSeckillResult(true, successKilled);
                return seckillResult;
        }else{
            throw  new SeckillException(SeckillEnums.SECKILL_INNER_ERROR.getStateInfo());
        }
    }
}
