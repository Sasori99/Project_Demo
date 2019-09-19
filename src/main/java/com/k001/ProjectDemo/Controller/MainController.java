package com.k001.ProjectDemo.Controller;

import com.k001.ProjectDemo.Service.Employee;
import com.k001.ProjectDemo.Service.EmployeeDAO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class MainController {
    @Autowired
    EmployeeDAO employeeDAO;

    MainController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeDAO.getAllEmployee();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOneEmployeeByID(@RequestParam int ID){
        return ResponseEntity.ok(employeeDAO.getOneEmployeeByID(ID));

    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeDAO.addEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id,@RequestBody Employee employee){
        return ResponseEntity.ok(employeeDAO.updateOneEmployeeByID(id,employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        return ResponseEntity.ok(employeeDAO.deleteOneEmployeeByID(id));
    }
}
