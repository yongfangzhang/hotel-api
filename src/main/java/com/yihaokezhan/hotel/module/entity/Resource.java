package com.yihaokezhan.hotel.module.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.enums.FileType;
import com.yihaokezhan.hotel.common.enums.ResourceType;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 * 资源表 实体类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonView(V.S.class)
@TableName(value = "resource", autoResultMap = true)
public class Resource extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "resource";

    /**
     * 资源类型
     */
    private Integer type;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 归属类型
     */
    private Integer ownerType;

    /**
     * 归属人
     */
    private String ownerUuid;

    /**
     * 访问路径
     */
    private String visitPath;

    public String getTypeName() {
        return EnumUtils.getName(ResourceType.class, this.type);
    }

    public String getFileTypeName() {
        return EnumUtils.getName(FileType.class, this.fileType);
    }
}
