package com.jobportal.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.entity.File;
@Repository
public interface FileRepository {

    void save(File f);

    Optional<File> findById(String id);

    List<File> findAll();
    
}
