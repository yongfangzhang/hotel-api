package com.yihaokezhan.hotel.common.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.yihaokezhan.hotel.common.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

@Component
public class MyTenantLineHandler implements TenantLineHandler {

    @Override
    public String getTenantIdColumn() {
        return "tenant_uuid";
    }

    @Override
    public Expression getTenantId() {
        return new StringValue(StringUtils.defaultString(DynamicTenantHandler.getTenant(), ""));
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 忽略租户
        for (String reg : Constant.NO_TENANT_REG) {
            boolean match = tableName.equals(reg) || tableName.matches(reg);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
