package com.yihaokezhan.hotel.handler;

import java.util.Date;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yihaokezhan.hotel.utils.StringUtils;
import com.yihaokezhan.hotel.utils.UUIDUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自定义填充字段
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
        Date now = new Date();
        for (String field : insertFields) {
            if (metaObject.hasSetter(field)) {
                strictInsertFill(metaObject, field, Date.class, now);
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
        if (StringUtils.isBlank(uuid)) {
            strictInsertFill(metaObject, field, String.class, UUIDUtils.generateTimeBased());
        }
    }

    private void updateDate(MetaObject metaObject) {
        String field = "updatedAt";
        if (metaObject.hasSetter(field)) {
            strictUpdateFill(metaObject, field, Date.class, new Date());
        }
    }
}
