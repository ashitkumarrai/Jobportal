package com.jobportal.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.entity.Employer;
@Repository
public interface EmployerRepository {
    public Optional<Employer> saveOrUpdate(Employer employer);
    public Optional<Employer> findById(Long employerId);

    public List<Employer> findAll();

    public void deleteById(Long id);
}
