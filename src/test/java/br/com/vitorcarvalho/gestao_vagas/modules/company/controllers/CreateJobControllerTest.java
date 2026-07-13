package br.com.vitorcarvalho.gestao_vagas.modules.company.controllers;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient.MockMvcServerSpec;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.vitorcarvalho.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.utils.TestUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class CreateJobControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
    }

    @Test
    @DisplayName("Should Create Job When Company is Valid")
    public void shouldCreateJob_whenCompanyIsValid() throws Exception{
        var company = CompanyEntity.builder().name("COMPANY_NAME").username("COMPANY_USERNAME").password("1234567890").email("COMPANY@email.com").description("COMPANY_DESCRIPTION").build();
        
        company = companyRepository.saveAndFlush(company);

        var createJobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/").contentType(MediaType.APPLICATION_JSON).content(TestUtil.objectToJSON(createJobDTO)).header("Authorization", TestUtil.generateToken(company.getId(), "GESTAO_VAGAS768##"))).andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    @Test
    @DisplayName("Should Throw Exception When Company is Invalid")
    public void shouldThrowException_whenCompanyIsInvalid() throws Exception{
         var createJobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/").contentType(MediaType.APPLICATION_JSON).content(TestUtil.objectToJSON(createJobDTO)).header("Authorization", TestUtil.generateToken(UUID.randomUUID(), "GESTAO_VAGAS768##"))).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
