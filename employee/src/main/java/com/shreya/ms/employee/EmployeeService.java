package com.shreya.ms.employee;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Employee> getEmployeesByOrganizationId(String organizationId) {
        return repository.findByOrganizationId(organizationId);
    }

    public List<Employee> getEmployeesByDepartmentId(String departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    public void deleteEmployee(String id) {
        repository.deleteById(id);
    }
}
