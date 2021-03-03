package com.yihaokezhan.hotel.common.remark;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
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

    public RemarkRecord() {
    }

    public RemarkRecord(String content) {
        this.content = content;
        this.createdAt =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_PATTERN));
    }
}
