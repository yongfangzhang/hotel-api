package com.yihaokezhan.hotel.common.handler;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yihaokezhan.hotel.common.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自定义填充字段
 * 
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        insertDate(metaObject);
        insertOrderedUuid(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        updateDate(metaObject);
    }

    private void insertDate(MetaObject metaObject) {
        String[] insertFields = new String[] {"createdAt", "updatedAt"};
        LocalDateTime now = LocalDateTime.now();
        for (String field : insertFields) {
            if (metaObject.hasSetter(field)) {
                setFieldValByName(field, now, metaObject);
            }
        }
    }

    // 生成有序uuid
    private void insertOrderedUuid(MetaObject metaObject) {
        String field = "uuid";
        if (!metaObject.hasGetter(field)) {
            return;
        }
        Object uuid = getFieldValByName(field, metaObject);
        if (uuid == null || StringUtils.isBlank(uuid.toString())) {
            setFieldValByName(field, UUIDUtils.generateTimeBased(), metaObject);
        }
    }

    private void updateDate(MetaObject metaObject) {
        String field = "updatedAt";
        if (metaObject.hasSetter(field)) {
            setFieldValByName(field, LocalDateTime.now(), metaObject);
        }
    }
}
