package com.jeeProject.weka.controller;

import com.jeeProject.weka.model.CatnDog;
import com.jeeProject.weka.model.MyUploadForm;
import com.jeeProject.weka.repository.CatnDogRepository;
import com.jeeProject.weka.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadFileController {

    @Autowired
    CatnDogRepository catnDogRepository;

    @GetMapping("/uploadOneFile")
    public String uploadOneFileHandler(Model model) {

        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "uploadOneFile";
    }

    @PostMapping("/uploadOneFile")
    public String uploadOneFileHandlerPOST(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    private String doUpload(HttpServletRequest request, Model model, //
                            MyUploadForm myUploadForm) {

        String description = myUploadForm.getDescription();

        // Root Directory.
        String uploadRootPath = request.getServletContext().getRealPath("upload");

        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = myUploadForm.getFileDatas();
        //
        List<File> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        File serverFile = null;

        for (MultipartFile fileData : fileDatas) {

            // Client File Name
            String name = fileData.getOriginalFilename();

            if (name != null && name.length() > 0) {
                try {
                    // Create the file at server
                    serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    uploadedFiles.add(serverFile);
                } catch (Exception e) {
                    failedFiles.add(name);
                }
            }
        }
        CommandeService commandeService = new CommandeService();
        String output = null;
        if (serverFile != null)
            output = commandeService.getOutput(serverFile);
        CatnDog catnDog = new CatnDog(description, output);
        catnDogRepository.save(catnDog);
        model.addAttribute("description", description);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "uploadResult";
    }

}
