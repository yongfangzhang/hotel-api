package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
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
    @NotBlank(message = "订单不能为空", groups = AddGroup.class)
    private String orderUuid;

    /**
     * 商品UUID
     */
    private String productUuid;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空", groups = AddGroup.class)
    private String productName;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空", groups = AddGroup.class)
    @PositiveOrZero(message = "商品价格无效", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空", groups = AddGroup.class)
    @Positive(message = "商品数量无效", groups = {AddGroup.class, UpdateGroup.class})
    private Integer productCount;

    /**
     * 是否押金支付
     */
    private boolean paidByDeposit;

    @TableField(exist = false)
    private boolean exsited;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    public void updateTotalPrice() {
        if (this.productPrice == null || this.productCount == null) {
            this.totalPrice = null;
            return;
        }
        this.totalPrice = this.productPrice.multiply(BigDecimal.valueOf(this.productCount));
    }
}
