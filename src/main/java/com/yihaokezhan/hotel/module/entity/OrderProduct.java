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
 * 订单商品表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "order_product", autoResultMap = true)
public class OrderProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "order_product";


    /**
     * 订单UUID
     */
    private String orderUuid;

    /**
     * 租户UUID
     */
    private String tenantUuid;

    /**
     * 商品UUID
     */
    private String productUuid;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice = BigDecimal.ZERO;

    /**
     * 商品数量
     */
    private Integer productCount = 1;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
