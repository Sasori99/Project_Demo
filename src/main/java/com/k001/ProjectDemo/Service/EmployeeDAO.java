package com.k001.ProjectDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class EmployeeDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("employee");

        // Commented as the database is H2, change the database and create procedure READ_employee before calling getEmployeeUsingSimpleJdbcCall
        simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("READ_employee");
    }

    public int getCountOfemployee(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM employee",Integer.class);
    }
//    POST
    public int addEmployee(final Employee employee){
        final Map<String,Object> parameter = new HashMap<String, Object>();
        parameter.put("ID",employee.getID());
        parameter.put("Name",employee.getName());
        parameter.put("Address",employee.getAddress());
        return simpleJdbcInsert.execute(parameter);
    }

//    GET
    public List<Employee> getAllEmployee(){
        return jdbcTemplate.query("SELECT * FROM employee",new EmployeeRowMapper());
    }

    public Employee getOneEmployeeByID(int id){
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        String name =  namedParameterJdbcTemplate.queryForObject("SELECT Name FROM employee WHERE ID = :id", namedParameters, String.class);
        String address =  namedParameterJdbcTemplate.queryForObject("SELECT Address FROM employee WHERE ID = :id", namedParameters, String.class);
        return new Employee(name,id,address);
    }
//    PUT
    public int updateOneEmployeeByID(int id,Employee newEmployee){
        return jdbcTemplate.update("UPDATE employee SET NAME = ? , ADDRESS = ? WHERE ID = ?", newEmployee.getName(), newEmployee.getName(), id);
    }
//    DELETE
    public int deleteOneEmployeeByID(int id){
        return jdbcTemplate.update("DELETE FROM employee WHERE ID = ?",id);
    }
}
