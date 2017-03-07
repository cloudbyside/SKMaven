package com.ylz.dao;

import com.ylz.dto.UserSucessKillsDTO;
import com.ylz.entity.SuccessKilled;
import com.ylz.entity.SuccessKilledKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuccessKilledMapper {
    int deleteByPrimaryKey(SuccessKilledKey key);

    int insertSelective(SuccessKilled record);

    SuccessKilled selectByPrimaryKey(SuccessKilledKey key);

    int updateByPrimaryKeySelective(SuccessKilled record);

    /**
     * 按照用户手机号进行查询已秒杀产品
     * @param userPhone
     * @return
     */
    List<UserSucessKillsDTO> queryUserResultsByPage(
            @Param("userPhone") Long userPhone,
            @Param("begin") int begin,
            @Param("offset") int offset);

    /**
     * 查看总记录条数
     * @param userPhone
     * @return
     */
    int queryUserResultsCount(Long userPhone);

}