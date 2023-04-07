package com.jobportal.system.repositoryimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.Candidate;
import com.jobportal.system.repository.CandidateRepository;

import lombok.extern.log4j.Log4j2;

@Repository

@Log4j2
public class CandidateRepositoryImpl implements CandidateRepository {

    @Override
    public Optional<Candidate> saveOrUpdate(Candidate candidate) {

        SystemApplication.candidates.add(candidate);
        SystemApplication.users.add(candidate.getUser());
        return findById(candidate.getUser().getId());
       
    }

    @Override
    public List<Candidate> findAll() {

        return SystemApplication.candidates;
       
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent((candidate)->{SystemApplication.candidates.remove(candidate);}
        );
        
    
       


        
    }

    @Override
    public Optional<Candidate> findById(Long candidateId) {
        return SystemApplication.candidates.stream().filter((Candidate c) -> c.getUser().getId().equals(candidateId))
                .findFirst();
    }

    @Override
    public Optional<Candidate> findByUsername(String string) {
        

        log.info(SystemApplication.candidates.stream().filter(( c) -> c.getUser().getUsername().equals(string))
        .findFirst());

        return SystemApplication.candidates.stream().filter(( c) -> c.getUser().getUsername().equals(string))
        .findFirst();
        
    }

    @Override
    public Optional<Candidate> updateCandidateById(Long id,Candidate candidate) {
        Optional<Candidate> result =findById(id).map(existingCandidate -> {
            if (candidate.getUser().getFirstName() != null) {
                existingCandidate.getUser().setFirstName(candidate.getUser().getFirstName());
            }

            if (candidate.getUser().getLastName() != null) {
                existingCandidate.getUser().setLastName(candidate.getUser().getLastName());
            }

            if (candidate.getUser().getEmail() != null) {
                existingCandidate.getUser().setEmail(candidate.getUser().getEmail());
            }

            if (candidate.getUser().getAdress() != null) {
                existingCandidate.getUser().setAdress(candidate.getUser().getAdress());
            }
            if (candidate.getUser().getImageUrl() != null) {
                existingCandidate.getUser().setImageUrl(candidate.getUser().getImageUrl());
            }
            if (candidate.getUser().getContact() != null) {
                existingCandidate.getUser().setContact(candidate.getUser().getContact());
            }
        

            if (candidate.getUser().getUsername() != null) {
                existingCandidate.getUser().setUsername(candidate.getUser().getUsername());
            }

            if (candidate.getCertification() != null) {
                existingCandidate.setCertification(candidate.getCertification());

                SystemApplication.certifications.addAll(candidate.getCertification());
            }

            if (candidate.getEducation() != null) {
                existingCandidate.setEducation(candidate.getEducation());

                SystemApplication.educations.addAll(candidate.getEducation());
            }
            if (candidate.getSkills() != null) {
                existingCandidate.setSkills(candidate.getSkills());
            
            }
            if (candidate.getWorkExperiences() != null) {
                existingCandidate.setWorkExperiences(candidate.getWorkExperiences());

                SystemApplication.workExperiences.addAll(candidate.getWorkExperiences());
            }
            if (candidate.getResumeUrl() != null) {
                existingCandidate.setResumeUrl(candidate.getResumeUrl());
            }

            return existingCandidate;
        });
        return result;
    }


    
}
