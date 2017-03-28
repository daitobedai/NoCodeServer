package com.daitobedai.configs;


import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListStringTypeHandler implements TypeHandler<List> {
    public void setParameter(PreparedStatement preparedStatement, int i, List list, JdbcType jdbcType) throws SQLException {
        if (list != null && !list.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < list.size(); j++) {
                sb.append(list.get(j)).append(",");
            }
            String value = sb.toString().substring(0, sb.toString().length() - 1);

            preparedStatement.setString(i, value);
        } else {
            preparedStatement.setString(i, null);
        }
    }


    public List getResult(ResultSet resultSet, String columnName) throws SQLException {
        return tranferType(resultSet.getString(columnName));

    }


    public List getResult(ResultSet resultSet, int i) throws SQLException {
        return tranferType(resultSet.getString(i));
    }


    public List getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return tranferType(callableStatement.getString(columnIndex));
    }

    private List tranferType(String result) {
        List<String> list = new ArrayList<String>();
        if (result != null && !result.isEmpty()) {
            String[] results = result.split("\\,");
            for (int i = 0; i < results.length; i++) {
                list.add(results[i]);
            }
            return list;
        } else {
            return list;
        }
    }
}
