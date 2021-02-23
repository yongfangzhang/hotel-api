package com.yihaokezhan.hotel.common.remark;

import java.time.LocalDateTime;
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
        appendRemark(entity, new RemarkEntity());
    }

    public static <T extends RemarkEntity> void appendRemark(T entity, RemarkRecord record) {
        if (record == null) {
            return;
        }
        if (CollectionUtils.isEmpty(entity.getRemark())) {
            entity.setRemark(initList(record));
        } else {
            entity.getRemark().add(record);
        }
    }

    public static <T extends RemarkEntity> void appendRemark(T entity, RemarkEntity remarkEntity) {
        if (entity == null) {
            return;
        }
        if (remarkEntity == null) {
            // 新建
            remarkEntity = new RemarkEntity();
        }
        // 把原有的拿过来
        entity.setRemark(remarkEntity.getRemark());
        if (entity.getRemarkRecord() == null) {
            return;
        }
        entity.getRemark().add(entity.getRemarkRecord());
    }

    public static <T extends RemarkEntity> void createRemarkRecord(T entity,
            String defaultContent) {
        if (entity == null) {
            return;
        }
        entity.setRemark(null); // 不要带过来的remark
        if (StringUtils.isBlank(entity.getRemarkContent())) {
            entity.setRemarkContent(defaultContent);
        }
        if (StringUtils.isBlank(entity.getRemarkContent())) {
            return;
        }
        RemarkRecord record = new RemarkRecord();
        record.setContent(entity.getRemarkContent());
        record.setCreatedAt(LocalDateTime.now().toString());
        entity.setRemarkRecord(record);
        entity.setRemarkContent(null);
    }

    public static <T extends RemarkEntity> void createRemarkRecord(T entity) {
        createRemarkRecord(entity, null);
    }

    private static List<RemarkRecord> initList(RemarkRecord r) {
        List<RemarkRecord> recordList = new ArrayList<>();
        recordList.add(r);
        return recordList;
    }
}
