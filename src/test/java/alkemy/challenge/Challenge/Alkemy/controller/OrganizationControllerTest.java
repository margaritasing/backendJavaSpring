package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.Organization;
import alkemy.challenge.Challenge.Alkemy.model.Slide;
import alkemy.challenge.Challenge.Alkemy.repository.OrganizationRepository;
import alkemy.challenge.Challenge.Alkemy.repository.SlideRepository;
import alkemy.challenge.Challenge.Alkemy.service.ActivityService;
import alkemy.challenge.Challenge.Alkemy.service.OrganizationService;
import alkemy.challenge.Challenge.Alkemy.service.SlideService;
import com.amazonaws.Response;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private SlideService slideService;

    @Autowired
    ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void getOngById() throws Exception {
        Long id = 1L;
        Optional<Organization> organization = repository.findOrganizationById(1L);
        assertThat(organization).isNotNull();
    }

    @Test
    @Rollback(value = false)
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void updateOng() throws Exception {
        Long id = 1L;
        Organization newOrganization = new Organization(id, "Name", "Image", "Address", 351, "S", "Welcome", "About");

        String url = "/public/1";
        MvcResult mvcResult = mockMvc.perform(
                post(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newOrganization))
                .with(csrf())
        ).andExpect(status().isNotFound())
                .andDo(print()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        String ongId = response;

        Organization savedOng = repository.findOrganizationById(id).get();
        assertThat(savedOng.getId()).isEqualTo(id);

    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    public void getSlidesById() {
        Long id = 1L;
        Organization organization = new Organization(1L, "Name", "Image", "Address", 351, "S", "Welcome", "About");
        Organization organizationAux = slideRepository.findById(1L).orElseThrow().getOrganizationId();
        assertThat(organizationAux).isNotNull();
    }


}
