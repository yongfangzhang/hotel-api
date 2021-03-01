package com.yihaokezhan.hotel.module.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 市表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonView(V.S.class)
@TableName(value = "comm_city", autoResultMap = true)
public class CommCity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "comm_city";

    /**
     * code
     */
    @TableId(type = IdType.INPUT)
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * province code
     */
    private String provinceCode;
}
