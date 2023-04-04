package com.jobportal.system.controller;


import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.jobportal.system.entity.File;
import com.jobportal.system.exceptionhandler.RecordNotFoundException;
import com.jobportal.system.repository.FileRepository;




@Slf4j
@RestController
public class FileController {


  

    @Autowired
    FileRepository fr;

  
    @PostMapping("/media/upload")
	public @ResponseBody ResponseEntity<?> uploadmedia( HttpServletRequest request,final @RequestBody MultipartFile file) {
		try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String name = file.getOriginalFilename();
            log.info(name); //download.jpg

            if (name == null || name.contains("..") || name.contains(" ")) {
                Map<String, String> hasMap = new HashMap<>();
   
           hasMap.put("Error", "Sorry! Filename contains invalid path sequence, correct name format before upload");
        
        

      
        return new ResponseEntity<Map<String,String>>(hasMap,HttpStatus.BAD_REQUEST);
			
            
			}
            
            byte[] mediaData = file.getBytes();


            File f = File.builder().media(mediaData)
        
                    .mediaContentType(file.getContentType().toString()).id(UUID.randomUUID().toString()+file.getOriginalFilename()).build();
            
                    
            fr.save(f);

            
        
         
            Map<String, String> hasMap = new HashMap<>();
            hasMap.put("mediaUrl", new URI("/media/show/" + f.getId()).toString());
            

            return new ResponseEntity<>(hasMap, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
    @GetMapping("/media/show/{id}")
    @ResponseBody
    
	void showMedia(@PathVariable("id") String id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<File> ff = fr.findById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, application/pdf,application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        if (!ff.isPresent()) {
            try {
                throw new RecordNotFoundException("media not found with id: " + id);
            } catch (RecordNotFoundException e) {
              
            }
        }
        response.getOutputStream().write(ff.get().getMedia());
        response.getOutputStream().close();
    }
    @GetMapping("/media/show/all")
	List< File> showAllMedia() {
       
        return fr.findAll();
    }

}
