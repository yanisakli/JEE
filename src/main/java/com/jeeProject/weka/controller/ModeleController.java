package com.jeeProject.weka.controller;

import com.jeeProject.weka.model.Modele;
import com.jeeProject.weka.repository.ModeleRepository;
import com.jeeProject.weka.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class ModeleController {

    @Autowired
    private ModeleRepository modeleRepository;
    @Autowired
    private FileStorageService storageService;



    @PostMapping("/modele")
    public Modele createModele(@Valid @RequestBody Modele modele) {
        return modeleRepository.save(modele);
    }

    @GetMapping("/modeles")
    public List getAllModeles() {
        return modeleRepository.findAll();
    }

    @GetMapping("/modele/{id}")
    public Modele getModelesById(@PathVariable(value = "id") Long modeleID) {
        return modeleRepository.findById(modeleID).get();
    }

    @PutMapping("/modele/{id}")
    public Modele updateModele(@PathVariable(value = "id") Long modeleID, @Valid @RequestBody Modele modeleDetails) {
        Modele modele = modeleRepository.findById(modeleID).get();
        if (!modeleDetails.getName().isEmpty()) {
            modele.setName(modeleDetails.getName());
        }
        return modeleRepository.save(modele);
    }

    @DeleteMapping("/modele/{id}")
    public boolean deleteModele(@PathVariable(value = "id") Long modeleID) {
        Modele modele = modeleRepository.findById(modeleID).get();
        if (!modele.getName().equals("")) {
            modeleRepository.delete(modele);
            return true;
        }
        return false;
    }

    @PostMapping("/modele/{id}")
    public Modele uploadFile(@PathVariable(value = "id") Long modeleID, @RequestParam("file") MultipartFile file) {
        String fileName = storageService.storeFile(file);
        Modele modele = modeleRepository.findById(modeleID).get();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/modeledownload/")
                .path(fileName)
                .toUriString();

        modele.setFile(fileDownloadUri);
        return modeleRepository.save(modele);
    }

    @GetMapping("/modeledownload/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "id") Long modeleID, HttpServletRequest request) {
        // Load file as Resource
        Modele modele = modeleRepository.findById(modeleID).get();
        Resource resource = storageService.loadFileAsResource(modele.getFile());
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}