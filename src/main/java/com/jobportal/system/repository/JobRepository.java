package com.jobportal.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.entity.Job;
@Repository
public interface JobRepository {
    public Optional<Job> saveOrUpdate(Job job);
    public Optional<Job> findById(Long jobId);

    public List<Job> findAll();

    public void deleteById(Long id);
}
