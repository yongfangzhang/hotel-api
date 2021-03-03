package com.yihaokezhan.hotel.common.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author zhangyongfang
 * @since Wed Mar 03 2021
 */
public abstract class ArrayTypeHandler<T> extends BaseTypeHandler<Object> {

    public abstract List<T> parseArray(String json);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter,
            JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseArray(rs.getString(columnName));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return parseArray(rs.getString(columnIndex));
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseArray(cs.getString(columnIndex));
    }
}
