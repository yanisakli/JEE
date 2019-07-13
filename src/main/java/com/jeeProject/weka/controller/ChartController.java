package com.jeeProject.weka.controller;

import com.jeeProject.weka.exception.NotFoundException;
import com.jeeProject.weka.model.Chart;
import com.jeeProject.weka.model.Modele;
import com.jeeProject.weka.repository.ChartRepository;
import com.jeeProject.weka.repository.ModeleRepository;
import com.jeeProject.weka.service.ChartHandlerService;
import com.jeeProject.weka.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class ChartController {


    @Autowired
    private ChartRepository chartRepository;
    @Autowired
    private ModeleRepository modeleRepository;
    @Autowired
    private FileStorageService storageService;
    private ChartHandlerService chartHandlerService = new ChartHandlerService();


    @PostMapping("/chart")
    public Chart createChart(@Valid @RequestBody Chart chart) {
        return chartRepository.save(chart);
    }

    @PutMapping("/lineChart/{id}")
    public Chart lineChart(@PathVariable(value = "id") Long chartID, @Valid @RequestBody long modeleID) {
        List<Chart> charts = chartRepository.findAll();
        Modele modele = modeleRepository.findById(modeleID).get();
        Chart chart = new Chart();
        for (Chart chart1 : charts) {
            if (chartID.equals(chart1.getId())) {
                chart = chart1;
            }
        }
        if (chart.getName().isEmpty()) {
            throw new NotFoundException("Chart hasn't been found");
        }

        return chart;
    }


    @GetMapping("/chartDownload/{fileName:.+}")
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


}
