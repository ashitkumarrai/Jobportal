package com.jobportal.system.repositoryimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.File;
import com.jobportal.system.repository.FileRepository;
@Repository
public class FileRepositoryImpl implements FileRepository{

    @Override
    public void save(File f) {

        SystemApplication.files.add(f);
        
    }

    @Override
    public Optional<File> findById(String id) {

        return SystemApplication.files.stream().filter((File c) -> c.getId().equals(id))
        .findFirst();


       
    }

    @Override
    public List<File> findAll() {

        return SystemApplication.files;
       
    }
    
}
