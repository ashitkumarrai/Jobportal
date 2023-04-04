package com.jobportal.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.entity.Candidate;


@Repository
public interface CandidateRepository {

    public Optional<Candidate> saveOrUpdate(Candidate candidate);
    public Optional<Candidate> findById(Long candidateId);

    public List<Candidate> findAll();

    public void deleteById(Long id);
    public Optional<Candidate> findByUsername(String string);
    public Optional<Candidate> updateCandidateById(Long id, Candidate candidate);


    
}
