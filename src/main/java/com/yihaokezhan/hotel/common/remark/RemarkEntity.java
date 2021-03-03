package com.yihaokezhan.hotel.common.remark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.handler.RemarkTypeHandler;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
@Setter
@JsonView(V.XS.class)
public class RemarkEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(typeHandler = RemarkTypeHandler.class)
    private List<RemarkRecord> remark = new ArrayList<>();

    @TableField(exist = false)
    private String remarkContent;
}
