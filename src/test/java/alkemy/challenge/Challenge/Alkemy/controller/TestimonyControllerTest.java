package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.Testimony;
import alkemy.challenge.Challenge.Alkemy.repository.TestimonyRepository;
import alkemy.challenge.Challenge.Alkemy.service.TestimonyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class TestimonyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestimonyService testimonyService;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    TestimonyController testimonyController;

    @Mock
    TestimonyRepository testimonyRepository;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void listTestimony() throws Exception {
        List<Testimony> list = new ArrayList<>();

        list.add(new Testimony(12L,
                "testimony",
                "testimony.jpg",
                "content",
                false,
                null,
                null));
        list.add(new Testimony(12L,
                "testimony",
                "testimony.jpg",
                "content",
                false,
                null,
                null));
        given(testimonyRepository.findAll()).willReturn(list);

        String jsonRequest = objectMapper.writeValueAsString(list);
        String url = "/testimonials";


        MvcResult result = mvc.perform(get(url).content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void newTestimony() throws Exception {
        Testimony testimony = new Testimony(12L, "testimony",
                "testimony.jpg",
                "content",
                false,
                null,
                null);
        String jsonRequest = objectMapper.writeValueAsString(testimony);
        MvcResult result = mvc.perform(post("/testimonials").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void saveResource() throws Exception {
        Testimony testimony = new Testimony(12L, "testimony",
                "testimony.jpg",
                "content",
                false,
                null,
                null);
        String url = "/testimonials" + "/{id}";
        mvc.perform(MockMvcRequestBuilders.put(url, testimony.getId()).content(mapToJson(testimony))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteTestimony() throws Exception {
        Testimony testimony = new Testimony(15L,
                "testimony2",
                "testimony2.jpg",
                "content2",
                false,
                null,
                null);
        testimonyService.createTestimony(testimony);
        given(testimonyService.deleteTestimony(testimony.getId()))
                .willReturn(null);

        this.mvc
                .perform(delete("/testimonials" + "/{id}", testimony.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
}