package com.yihaokezhan.hotel.common.remark;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class RemarkUtils {

    public static <T extends RemarkEntity> void appendRemark(T entity) {
        appendRemark(entity, null);
    }

    public static <T extends RemarkEntity> void appendRemark(T entity, List<RemarkRecord> remark) {
        String newContent = entity.getRemarkContent();
        if (StringUtils.isBlank(newContent)) {
            return;
        }
        if (CollectionUtils.isEmpty(remark)) {
            remark = new ArrayList<>();
        }
        remark.add(new RemarkRecord(newContent));
        entity.setRemark(remark);
    }


    public static <T extends RemarkEntity> void appendRemark(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        entities.forEach(entity -> {
            appendRemark(entity, null);
        });
    }

}
