package com.yihaokezhan.hotel.common.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import org.apache.commons.lang3.ArrayUtils;
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
        return new StringValue(DynamicTenantHandler.getTenant());
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 忽略租户
        String[] ignores = new String[] {"tenant", "comm_area", "comm_city", "comm_province"};
        return DynamicTenantHandler.isRootTenant() || ArrayUtils.contains(ignores, tableName);
    }
}
