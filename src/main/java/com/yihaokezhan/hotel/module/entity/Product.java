package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 商品表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "product", autoResultMap = true)
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "product";

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price = BigDecimal.ZERO;
}
