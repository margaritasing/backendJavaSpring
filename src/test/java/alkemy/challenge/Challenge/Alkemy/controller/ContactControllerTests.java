package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.Contact;
import alkemy.challenge.Challenge.Alkemy.service.ContactService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class ContactControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    public void bringAllContactsTest() throws Exception {
        List<Contact> listContacts = new ArrayList<>();

        listContacts.add(new Contact(
                1L,
                "Pepe",
                44443322,
                "Pepe@alkemy.com",
                "contacto de mi casa",
                false));
        listContacts.add(new Contact(
                2L,
                "Juan",
                44445555,
                "Juan@alkemy.com",
                "contacto de mi celular",
                true));

        Mockito.when(contactService.bringAllContacts()).thenReturn(listContacts);
        mockMvc.perform(get("/contacts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    public void bringAllContactsWrongCredentials() throws Exception {
        List<Contact> listContacts = new ArrayList<>();

        listContacts.add(new Contact(
                1L,
                "Pepe",
                44443322,
                "Pepe@alkemy.com",
                "contacto de mi casa",
                false));
        listContacts.add(new Contact(
                2L,
                "Juan",
                44445555,
                "Juan@alkemy.com",
                "contacto de mi celular",
                true));
        Mockito.when(contactService.bringAllContacts()).thenReturn(listContacts);
        mockMvc.perform(get("/contacts"))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));

    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    public void createContact() throws Exception {
        Contact contact = new Contact(
                3L,
                "Pedro",
                44445555,
                "Pedrito@alkemy.com",
                "creo el contacto",
                false);
        String jsonRequest = objectMapper.writeValueAsString(contact);
        mockMvc.perform(post("/contacts").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    public void createContactWithBlank() throws Exception {
        Contact contact = new Contact(
                3L,
                "",
                44445555,
                "JuanCarlos@alkemy.com",
                "creo el contacto",
                false);
        String jsonRequest = objectMapper.writeValueAsString(contact);
        mockMvc.perform(post("/contacts").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    public void listContact() throws Exception {
        Contact contact = new Contact(
                4L,
                "Federico",
                12345678,
                "ElFede@alkemy.com",
                "El Fede",
                false);
        given(contactService.listContact(contact.getId()))
                .willReturn(contact);

        this.mockMvc.perform(get("/backoffice/contacts/{id}", 4)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    public void listContactDoesntExists() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/backoffice/contacts/{id}", 25)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertEquals("", mvcResult.getResponse().getContentAsString());
    }
}