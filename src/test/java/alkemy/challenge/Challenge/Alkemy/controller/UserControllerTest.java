package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.User;
import alkemy.challenge.Challenge.Alkemy.repository.RoleRepository;
import alkemy.challenge.Challenge.Alkemy.repository.UserRepository;
import alkemy.challenge.Challenge.Alkemy.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com")
    void listUsers() throws Exception{
        List<User> listUsers = new ArrayList<>();

        listUsers.add(new User("test", "test", "admin@alkemy.com", "12345678", " ", roleRepository.findById(2L).get()));
        listUsers.add(new User("test2", "test2", "admin2@alkemy.com", "123456789", " ", roleRepository.findById(2L).get()));

        Mockito.when(userController.listUsers()).thenReturn(listUsers);
        String url = "/users";

        RequestBuilder request = get(url);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void saveUser() throws Exception{
        User test = new User("test", "test", "admin@alkemy.com", "12345678", " ",roleRepository.findById(2L).get());
        
        Mockito.when(userService.saveUser(test)).thenReturn("ok");
        String url = "/users";

        RequestBuilder request = get(url);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    User createTestUser(String email, String password) throws Exception{
        User user = new User("firstName", "lastName", email, bCryptPasswordEncoder.encode(password), "photo", null);
        userService.saveUser(user);
        return user;
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteUserByIdTest1()throws Exception{
        User user = createTestUser("test@alkemy.com","password");
        user.setDeleted(false);
        
        Mockito.when(userController.deleteUserById(user.getId())).thenReturn(new ResponseEntity(HttpStatus.OK));
        String url = "/users";

        RequestBuilder request = get(url);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteUserByIdTest2()throws Exception{
        
        Mockito.when(userController.deleteUserById(1L)).thenReturn(new ResponseEntity(HttpStatus.NOT_FOUND));
        String url = "/users";

        RequestBuilder request = get(url);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }
}
