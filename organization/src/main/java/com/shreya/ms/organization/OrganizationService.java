package com.shreya.ms.organization;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository repository;

    public OrganizationService(OrganizationRepository repository) {
        this.repository = repository;
    }

    public Organization createOrganization(Organization organization) {
        return repository.save(organization);
    }

    public List<Organization> getAllOrganizations() {
        return repository.findAll();
    }

    public Organization getOrganizationById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteOrganization(String id) {
        repository.deleteById(id);
    }
}
