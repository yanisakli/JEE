package weka.com;

import com.jeeProject.weka.WekaApplication;
import com.jeeProject.weka.controller.UserController;
import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import weka.WekaApplicationTests;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes= WekaApplication.class)
public class TestUserRepository {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateUserShouldBeOk() throws Exception {
        // given
        User barto = new User("barto","barto");
        String content = "{\"name\":\"barto\", \"password\":\"barto\"}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse());
    }

    @Test
    public void testCreateUserWithSameUsername()throws Exception{
        User barto = new User("barto","barto");
        String content = "{\"name\":\"barto\", \"password\":\"barto\"}";
        List<User> allUsers = Arrays.asList(barto);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(result.getResponse());
    }

    @Test
    public void testAuthWithGoodCredential()throws Exception{
       String password = BCrypt.hashpw("barto",BCrypt.gensalt());
        User barto = new User("barto", password);

        String content = "{\"name\":\"barto\", \"password\":\"barto\"}";
        List<User> allUsers = Arrays.asList(barto);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void checkAuthToken()throws Exception{
        String password = BCrypt.hashpw("barto",BCrypt.gensalt());
        User barto = new User("barto", password);
        String content = "{\"name\":\"barto\", \"password\":\"barto\"}";
        List<User> allUsers = Arrays.asList(barto);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();
        System.out.println(result.getResponse());
    }

    @Test
    public void testAuthWithWrongCredential()throws Exception{
        String password = BCrypt.hashpw("barto",BCrypt.gensalt());
        User barto = new User("barto", password);
        String content = "{\"name\":\"barto\", \"password\":\"ee\"}";
        List<User> allUsers = Arrays.asList(barto);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isNotFound()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


}
