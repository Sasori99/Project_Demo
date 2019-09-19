package com.k001.ProjectDemo.Service;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {
    public Employee mapRow(ResultSet rs,int rowNum) throws SQLException{
        Employee employee = new Employee();
        employee.setID(rs.getInt("ID"));
        employee.setName(rs.getString("Name"));
        employee.setAddress(rs.getString("Address"));
        return employee;
    }
}
