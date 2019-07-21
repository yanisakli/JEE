package integration.controller;

import com.jeeProject.weka.WekaApplication;
import com.jeeProject.weka.controller.ChartController;
import com.jeeProject.weka.controller.UserController;
import com.jeeProject.weka.model.Modele;
import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.ModeleRepository;
import com.jeeProject.weka.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChartController.class)
@ContextConfiguration(classes= WekaApplication.class)
public class TestChartController {

        @Autowired
        private MockMvc mvc;

        @MockBean
        private ModeleRepository modeleRepository;

/*
    @Test
    public void testGetModelesWhenEmpty() throws Exception {

        Mockito.when(modeleRepository.findAll()).thenReturn(Arrays.asList());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/modeles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
    }
    @Test
    public void testGetModelesReturnModeles() throws Exception {
        Modele model = new Modele("model",1);
        List<Modele> allModel = Arrays.asList(model);
        Mockito.when(modeleRepository.findAll()).thenReturn(allModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/modeles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
    */
    @Test
    public void testGetPieChartReturnOk() throws Exception {
        Modele model = new Modele("model",1);
        List<Modele> allModel = Arrays.asList(model);
        Mockito.when(modeleRepository.findAll()).thenReturn(allModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/displayBarGraph")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void testGetPieChartEmptyReturnNotFound() throws Exception {
        List<Modele> allModel = Arrays.asList();
        Mockito.when(modeleRepository.findAll()).thenReturn(allModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/displayBarGraph")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
    }
}
