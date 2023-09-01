package com.app.demo.utils.handler;

import com.google.gson.Gson;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StringListTypeHandler implements TypeHandler<List<String>> {

    private final Gson gson = new Gson();

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> list, JdbcType jdbcType) throws SQLException {
        if (list != null) {
            preparedStatement.setString(i, gson.toJson(list));
        } else {
            preparedStatement.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        return gson.fromJson(value, List.class);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return gson.fromJson(value, List.class);
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return gson.fromJson(value, List.class);
    }
}