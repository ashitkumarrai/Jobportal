package com.jobportal.system.repositoryimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.Employer;
import com.jobportal.system.repository.EmployerRepository;


@Repository
public class EmployerRepositoryImpl implements EmployerRepository{

    @Override
    public Optional<Employer> saveOrUpdate(Employer employer) {
        SystemApplication.employers.add(employer);
        return findById(employer.getUser().getId());
    }

    @Override
    public Optional<Employer> findById(Long employerId) {
        return SystemApplication.employers.stream().filter((Employer c) -> c.getUser().getId().equals(employerId))
        .findFirst();
    }

    @Override
    public List<Employer> findAll() {
        return SystemApplication.employers;
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent((employer)->{SystemApplication.employers.remove(employer);}
        );
    }
    
}
