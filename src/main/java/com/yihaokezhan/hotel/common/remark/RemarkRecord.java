package com.yihaokezhan.hotel.common.remark;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonView;
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
public class RemarkRecord implements Serializable {

    /**
    *
    */
    private static final long serialVersionUID = 4140363696288158703L;

    private String content;

    private String createdAt;
}
