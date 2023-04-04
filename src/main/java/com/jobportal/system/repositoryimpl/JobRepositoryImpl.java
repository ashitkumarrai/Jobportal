package com.jobportal.system.repositoryimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.Job;
import com.jobportal.system.repository.JobRepository;

@Repository
public class JobRepositoryImpl implements JobRepository{

    @Override
    public Optional<Job> saveOrUpdate(Job job) {
        SystemApplication.jobs.add(job);
        return findById(job.getId());
    }

    @Override
    public Optional<Job> findById(Long jobId) {
        return SystemApplication.jobs.stream().filter((Job c) -> c.getId().equals(jobId))
        .findFirst();
    }

    @Override
    public List<Job> findAll() {
        return SystemApplication.jobs;
        
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent((job)->{SystemApplication.jobs.remove(job);}
        );
    
    }
    
}
