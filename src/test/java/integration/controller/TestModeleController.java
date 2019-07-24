package integration.controller;

import com.jeeProject.weka.WekaApplication;
import com.jeeProject.weka.controller.ChartController;
import com.jeeProject.weka.controller.ModeleController;
import com.jeeProject.weka.model.Modele;
import com.jeeProject.weka.repository.ModeleRepository;
import com.jeeProject.weka.service.FileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(ModeleController.class)
@ContextConfiguration(classes= WekaApplication.class)
public class TestModeleController {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private ModeleRepository modeleRepository;

    @MockBean
    private FileStorageService storageService;

    @Test
    public void testGetPieChartReturnOk() throws Exception {
        Modele model = new Modele("model",1);
        List<Modele> allModel = Arrays.asList(model);
        Mockito.when(modeleRepository.findAll()).thenReturn(allModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/createModele")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
}