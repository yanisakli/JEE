package com.jeeProject.weka;


import com.jeeProject.weka.controller.UserController;
import com.jeeProject.weka.model.User;
import com.jeeProject.weka.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void deleteUserShouldWork() throws Exception {
        User user = new User("barto","barto");
        user.setToken("token");
        user.setToken_expiration(new Timestamp(System.currentTimeMillis() + 36000000));
        List<User> allUsers = Arrays.asList(user);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/user/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", "token"))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void deleteUserShouldCallDeleteFunction() throws Exception {
        User user = new User("barto","barto");
        user.setToken("token");
        user.setToken_expiration(new Timestamp(System.currentTimeMillis() + 36000000));
        List<User> allUsers = Arrays.asList(user);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/user/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", "token"))
                .andReturn();
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    public void deleteUserShouldNotWorkWithoutToken() throws Exception {
        User user = new User("barto","barto");
        user.setToken("token");
        user.setToken_expiration(new Timestamp(System.currentTimeMillis() + 36000000));
        List<User> allUsers = Arrays.asList(user);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/user/delete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    /*
    @PutMapping("/user/update")
    public User updateUser(@RequestHeader("x-access-token") String token, @Valid @RequestBody User newValue) {
        User user = getUserWithToken(token);
        Timestamp currentTImestamp = new Timestamp(System.currentTimeMillis());
        if (currentTImestamp.after(user.getToken_expiration())) {
            throw new UnauthorizedExecption("Token invalid, you have to re-authentified yourself !");
        }
        user.setName(newValue.getName());
        user.setPassword(BCrypt.hashpw(newValue.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }*/

    @Test
    public void updateShouldWork() throws Exception {
        User user = new User("barto","barto");
        user.setToken("token");
        String content = "{\"name\":\"batard\", \"password\":\"oui\"}";
        user.setToken_expiration(new Timestamp(System.currentTimeMillis() + 36000000));
        List<User> allUsers = Arrays.asList(user);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/user/delete")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }
}