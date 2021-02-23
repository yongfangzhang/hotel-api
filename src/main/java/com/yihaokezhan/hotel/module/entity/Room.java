package com.yihaokezhan.hotel.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.remark.RemarkEntity;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 房间表
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(autoResultMap = true)
public class Room extends RemarkEntity {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(type = IdType.INPUT)
    private String uuid;

    /**
     * 公寓UUID
     */
    private String apartmentUuid;

    /**
     * 楼号
     */
    private String floorNumber;

    /**
     * 单元号
     */
    private String unitNumber;

    /**
     * 房间号
     */
    private String number;

    /**
     * 房间类型
     */
    private Integer type;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 房间状态
     */
    private Integer state;

    /**
     * 销售次数
     */
    private Integer saleTimes;

    /**
     * 总收益
     */
    private BigDecimal income;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime updatedAt;


}
