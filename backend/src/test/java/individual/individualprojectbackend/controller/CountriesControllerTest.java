package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.GetCitiesByCountryUseCase;
import individual.individualprojectbackend.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CountriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCitiesByCountryUseCase getCitiesByCountryCode;

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void getAllCitiesByCountryCode_shouldReturn200WithCities_whenCountryCodeExists() throws Exception {
        City[] cities = new City[2];

        cities[0] = (City.builder()
                        .id(1L)
                        .name("Eindhoven")
                        .build());
        cities[1] = (City.builder()
                        .id(2L)
                        .name("Rotterdam")
                        .build());

        when(getCitiesByCountryCode.getCitiesByCountryCode("NL")).thenReturn(cities);

        mockMvc.perform(get("/api/countries/NL"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [
                            {
                              "id": 1,
                              "name": "Eindhoven"
                            },
                            {
                              "id": 2,
                              "name": "Rotterdam"
                            }
                        ]
            """));

        verify(getCitiesByCountryCode).getCitiesByCountryCode("NL");
    }
}