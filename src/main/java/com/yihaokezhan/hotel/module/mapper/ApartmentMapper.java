package com.yihaokezhan.hotel.module.mapper;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yihaokezhan.hotel.module.entity.Apartment;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 公寓表 Mapper 接口
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface ApartmentMapper extends BaseMapper<Apartment> {

    void updateIncome(@Param("uuid") String uuid, @Param("income") BigDecimal income, @Param("times") int times);
}
