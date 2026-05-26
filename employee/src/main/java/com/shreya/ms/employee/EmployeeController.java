package com.shreya.ms.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;
    private final DepartmentClient departmentClient;

    public EmployeeController(EmployeeService service, DepartmentClient departmentClient) {
        this.service = service;
        this.departmentClient = departmentClient;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(service.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(service.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = service.getEmployeeById(id);
        if (employee != null)
            return new ResponseEntity<>(employee, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/with-department")
    public ResponseEntity<EmployeeResponse> getEmployeeWithDepartment(@PathVariable String id) {
        Employee employee = service.getEmployeeById(id);
        if (employee == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Department department = departmentClient.getDepartmentById(employee.getDepartmentId());
        return new ResponseEntity<>(new EmployeeResponse(employee, department), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Employee>> getEmployeesByOrganizationId(@PathVariable String organizationId) {
        return new ResponseEntity<>(service.getEmployeesByOrganizationId(organizationId), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable String departmentId) {
        return new ResponseEntity<>(service.getEmployeesByDepartmentId(departmentId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        service.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
