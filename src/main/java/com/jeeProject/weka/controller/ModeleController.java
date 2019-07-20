package com.jeeProject.weka.controller;

import com.jeeProject.weka.exception.NotFoundException;
import com.jeeProject.weka.model.Modele;
import com.jeeProject.weka.repository.ModeleRepository;
import com.jeeProject.weka.repository.UserRepository;
import com.jeeProject.weka.service.FileStorageService;
import com.jeeProject.weka.service.WekaService;
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
    @Autowired
    private UserRepository userRepository;


    /**
     * Create modele like :
     * {
     * "name" : "modele",
     * "idUser" : 1
     * }
     * if user doesn't exist throw exception
     *
     * @param modele
     * @return
     */
    @PostMapping("/modele")
    public Modele createModele(@Valid @RequestBody Modele modele) {
        boolean userExist = userRepository.existsById(modele.getIdUser());
        if (!userExist) {
            throw new NotFoundException("User haven't been found");
        }
        return modeleRepository.save(modele);
    }

    /**
     * Get all modeles that exists like :
     * /modeles
     * throw not found if there is no model to show
     *
     * @return
     */
    @GetMapping("/modeles")
    public List getAllModeles() {
        List<Modele> modeles = modeleRepository.findAll();
        if (modeles.isEmpty()) {
            throw new NotFoundException("There is no model to show !");
        }
        return modeles;
    }

    /**
     * Get modele according to id in path like :
     * /modele/1
     * throw exception if not found
     *
     * @param modeleID
     * @return
     */
    @GetMapping("/modele/{id}")
    public Modele getModelesById(@PathVariable(value = "id") Long modeleID) {
        if (!modeleRepository.existsById(modeleID)) {
            throw new NotFoundException("Modele doesn't exist !");
        }
        return modeleRepository.findById(modeleID).get();
    }

    /**
     * Update modele according to id in path and modele in body like :
     * /modele/1
     * {
     * "name" : "modele"
     * }
     *
     * @param modeleID
     * @param name
     * @return
     */
    @PutMapping("/modele/{id}")
    public Modele updateModele(@PathVariable(value = "id") Long modeleID, @Valid @RequestBody String name) {
        if (!modeleRepository.existsById(modeleID)) {
            throw new NotFoundException("Modele doesn't exist !");
        }
        Modele modele = modeleRepository.findById(modeleID).get();
        if (!name.isEmpty()) {
            modele.setName(name);
        }
        return modeleRepository.save(modele);
    }

    /**
     * Delete modele according to id in path like :
     * /modele/1
     *
     * @param modeleID
     * @return
     */
    @DeleteMapping("/modele/{id}")
    public boolean deleteModele(@PathVariable(value = "id") Long modeleID) {
        if (!modeleRepository.existsById(modeleID)) {
            throw new NotFoundException("Modele doesn't exist !");
        }
        Modele modele = modeleRepository.findById(modeleID).get();
        if (!modele.getName().equals("")) {
            modeleRepository.delete(modele);
            return true;
        }
        return false;
    }

    /**
     * Upload file
     * @param modeleID
     * @param file
     * @return
     */
    @PostMapping("/modele/{id}")
    public Modele uploadFile(@PathVariable(value = "id") Long modeleID, @RequestParam("file") MultipartFile file) {
        if (!modeleRepository.existsById(modeleID)) {
            throw new NotFoundException("Modele doesn't exist !");
        }
        String fileName = storageService.storeFile(file);
        Modele modele = modeleRepository.findById(modeleID).get();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/modeledownload/")
                .path(fileName)
                .toUriString();

        modele.setFile(fileDownloadUri);
        return modeleRepository.save(modele);
    }

    /**
     * @param modeleID
     * @return
     */
    @PostMapping("/modeletrain/{id}")
    public Modele uploadFile(@PathVariable(value = "id") Long modeleID) {
        Modele modele = modeleRepository.findById(modeleID).get();
        String f = modele.getFile();


        return modeleRepository.save(modele);
    }


    /**
     * @param fileName
     * @param request
     * @return
     */
    @GetMapping("/modeledownload/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = storageService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * @param modeleID
     * @return
     * @throws Exception
     */
    @PutMapping("/modeleResult/{id}")
    public Modele putModelResult(@PathVariable(value = "id") Long modeleID) throws Exception {
        Modele modele = modeleRepository.findById(modeleID).get();

        WekaService wekaService = new WekaService(modele);

        modele = wekaService.ExecuteModel(modele);

        return modeleRepository.save(modele);
    }


}