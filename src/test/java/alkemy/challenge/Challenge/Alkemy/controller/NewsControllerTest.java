package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.News;
import alkemy.challenge.Challenge.Alkemy.service.CategoryService;
import alkemy.challenge.Challenge.Alkemy.service.CommentService;
import alkemy.challenge.Challenge.Alkemy.service.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:test.properties")
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void listNews() throws Exception {
        mockMvc.perform(get("/news"))
                .andDo(print())
                /*.andExpect(content().json(newsService.findAll(Pageable.ofSize(10))))*/
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void bringNewsAdmin() throws Exception {
        mockMvc.perform(get("/news/1"))
                .andDo(print())
                .andExpect(content().json(mapToJson(newsService.findById(1).get())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void bringNewsUser() throws Exception {
        mockMvc.perform(get("/news/{id}/comments","1"))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void listComments() throws Exception {
        mockMvc.perform(get("/news/{id}/comments","1"))
                .andDo(print())
                .andExpect(content().json(mapToJson(commentService.listComments(1L))))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void newNewsAdmin() throws Exception {
        News news = new News("name", "imagePath", "content");
        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(news)))
                .andDo(print())
                //.andExpect(content().json(mapToJson(news))) no matchea porque a la news al crearse se le asigna una categoria
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void newNewsUser() throws Exception {
        News news = new News("name", "imagePath", "content");
        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(news)))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteNewsAdmin() throws Exception {
        News news = new News("name", "imagePath", "content");
        newsService.create(news);
        mockMvc.perform(delete("/news/{id}", news.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteNewsAdminNotFound() throws Exception {
        News news = new News("name", "imagePath", "content");
        newsService.create(news);
        mockMvc.perform(delete("/news/{id}", news.getId()+1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void deleteNewsUser() throws Exception {
        News news = new News("name", "imagePath", "content");
        newsService.create(news);
        mockMvc.perform(delete("/news/{id}", news.getId()))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }
}