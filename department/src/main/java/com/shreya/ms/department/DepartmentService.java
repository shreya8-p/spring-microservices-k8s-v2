package com.shreya.ms.department;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public Department createDepartment(Department department) {
        return repository.save(department);
    }

    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    public Department getDepartmentById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Department> getDepartmentsByOrganizationId(String organizationId) {
        return repository.findByOrganizationId(organizationId);
    }

    public void deleteDepartment(String id) {
        repository.deleteById(id);
    }
}
