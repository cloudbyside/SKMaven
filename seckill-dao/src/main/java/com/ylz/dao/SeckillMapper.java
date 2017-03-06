package com.ylz.dao;

import com.ylz.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SeckillMapper {

    int deleteByPrimaryKey(Integer seckillId);

    int insertSelective(Seckill record);

    Seckill selectByPrimaryKey(Integer seckillId);

    //分页查询
    List<Seckill> selectByPage(
            @Param("begin") int begin,
            @Param("offset") int offset,
            @Param("sort") int sort,
            @Param("order") String order
    );

    //查询秒杀产品的总记录条数
    int selectTotalCount(@Param("sort")int sort);

    int updateByPrimaryKeySelective(Seckill record);

    //更新库存
    int updateSeckillNumber(int seckilId);

}