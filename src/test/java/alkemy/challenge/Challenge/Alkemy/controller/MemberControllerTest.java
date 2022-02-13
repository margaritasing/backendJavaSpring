package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.model.Member;
import alkemy.challenge.Challenge.Alkemy.service.MemberService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:test.properties")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void listMembersAdmin() throws Exception {
        mockMvc.perform(get("/members"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void listMembersUser() throws Exception {
        mockMvc.perform(get("/members"))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void newMemberAdmin() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(member)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void newMemberUser() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(member)))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteMemberAdmin() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        memberService.create(member);
        mockMvc.perform(delete("/members/{id}", member.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void deleteMemberNotFound() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        memberService.create(member);
        mockMvc.perform(delete("/members/{id}", member.getId()+1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void deleteMemberUser() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        memberService.create(member);
        mockMvc.perform(delete("/members/{id}", member.getId()))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void editMemberAdmin() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        memberService.create(member);
        Member memberEdit = new Member("name2","facebookUrl2","instagramUrl2","linkedinUrl2","image2","description2");
        mockMvc.perform(put("/members/{id}", member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(memberEdit)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@alkemy.com", roles = {"ADMIN"})
    void editMemberNotFound() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        memberService.create(member);
        Member memberEdit = new Member("name2","facebookUrl2","instagramUrl2","linkedinUrl2","image2","description2");
        mockMvc.perform(put("/members/{id}", member.getId()+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(memberEdit)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@alkemy.com", roles = {"USER"})
    void editMemberUser() throws Exception {
        Member member = new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description");
        memberService.create(member);
        Member memberEdit = new Member("name2","facebookUrl2","instagramUrl2","linkedinUrl2","image2","description2");
        mockMvc.perform(put("/members/{id}", member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(memberEdit)))
                .andDo(print())
                .andExpect(redirectedUrl("/deny"));
    }
}