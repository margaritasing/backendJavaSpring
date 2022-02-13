package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.User;
import alkemy.challenge.Challenge.Alkemy.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:test.properties")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    User createTestUser(String email, String password) throws Exception{
        User user = new User("firstName", "lastName", email, bCryptPasswordEncoder.encode(password), "photo", null);
        userService.saveUser(user);
        return user;
    }


    @Test
    void createAuthenticationToken() throws Exception {
        User user = createTestUser("test@alkemy.com","password");
        /*AuthenticationRequest userLoginData = new AuthenticationRequest("test@alkemy.com","password");*/
        String jsonBody = "{ \"username\":\"admin@alkemy.com\", \"password\":\"password\"}";
        mockMvc.perform(post("/authenticate")
                .content(jsonBody)//no loguea con usuario recien creado**
                .contentType(MediaType.APPLICATION_JSON)
                /*.content(mapToJson(userLoginData)))*/
                /*.content("{ "username": "test@alkemy.com", "password": "password" }")*/
                /*.param("username","test@alkemy.com")
                .param("password","password")*/
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createAuthenticationTokenWithBadCredentials() throws Exception {
        User user = createTestUser("test2@alkemy.com","password2");
        String jsonBody = "{ \"username\":\"wrongemail@alkemy.com\", \"password\":\"notapassword\"}";
        mockMvc.perform(post("/authenticate")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}