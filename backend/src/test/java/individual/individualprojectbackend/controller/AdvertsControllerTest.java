package individual.individualprojectbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import individual.individualprojectbackend.business.*;
import individual.individualprojectbackend.domain.*;
import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdvertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAdvertUseCase getAdvertUseCase;
    @MockBean
    private GetAllAdvertsUseCase getAllAdvertsUseCase;
    @MockBean
    private CreateAdvertUseCase createAdvertUseCase;
    @MockBean
    private UpdateAdvertUseCase updateAdvertUseCase;
    @MockBean
    private DeleteAdvertUseCase deleteAdvertUseCase;
    @MockBean
    private GetAdvertsByLandlord getAdvertsByLandlord;
    @MockBean
    private GetCityStatisticsUseCase getCityStatisticsUseCase;

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"TENANT"})
    void getAdvert_shouldReturn200WithAdvert_whenAdvertFound() throws Exception {
        Advert advert = Advert.builder()
                .id(1L)
                .price(1000)
                .description("Spacious 2-bedroom apartment for rent")
                .utilitiesIncluded(true)
                .creationDate(null)
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(Arrays.asList(new byte[0], new byte[0]))
                .build();

        when(getAdvertUseCase.getAdvert(1L)).thenReturn(advert);
        mockMvc.perform(get("/adverts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "id": 1,
                                "price": 1000.0,
                                "description": "Spacious 2-bedroom apartment for rent",
                                "property": {
                                    "id": null,
                                    "size": 0,
                                    "numberOfRooms": 0,
                                    "propertyType": null,
                                    "furnishingType": null,
                                    "address": {
                                        "id": null,
                                        "city": null,
                                        "street": null,
                                        "zipcode": null
                                    }
                                },
                                "creator": {
                                    "id": null,
                                    "name": null,
                                    "email": null,
                                    "description": null,
                                    "hashedPassword": null,
                                    "address": {
                                        "id": null,
                                        "city": null,
                                        "street": null,
                                        "zipcode": null
                                    },
                                    "gender": null,
                                    "birthDate": null,
                                    "profilePicture": null,
                                    "joinDate": null,
                                    "role": null,
                                    "status": null
                                },
                                "utilitiesIncluded": true,
                                "availableFrom": null,
                                "creationDate": null,
                                "photos": [
                                    "",
                                    ""
                                ]
                            }
                """));
        verify(getAdvertUseCase).getAdvert(1L);
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void createAdvert_shouldReturn201WithId_whenAdvertCreated() throws Exception {
        Advert advert = Advert.builder()
                .price(1500.00)
                .description("Spacious 2-bedroom apartment for rent")
                .utilitiesIncluded(true)
                .creationDate(null)
                .property(Property.builder()
                        .size(1000)
                        .numberOfRooms(2)
                        .propertyType(PropertyType.APARTMENT)
                        .furnishingType(PropertyFurnishingType.FURNISHED)
                        .address(Address.builder()
                                .city("Example City")
                                .street("123 Main Street")
                                .zipcode("12345")
                                .build())
                        .build())
                .creator(User.builder().id(1L).build())
                .availableFrom(LocalDate.of(2023, 7, 22))
                .photos(Arrays.asList(new byte[0], new byte[0]))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestJson = objectMapper.writeValueAsString(advert);

        when(createAdvertUseCase.createAdvert(any(Advert.class))).thenReturn(1L);

        mockMvc.perform(post("/adverts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "id": 1
                    }
                """));

        verify(createAdvertUseCase).createAdvert(any(Advert.class));
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void updateAdvert_shouldReturn204_whenAdvertUpdated() throws Exception {
        Advert updatedAdvert = Advert.builder()
                .id(1L)
                .price(1200)
                .description("Updated 2-bedroom apartment for rent")
                .utilitiesIncluded(true)
                .creationDate(null)
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(Arrays.asList(new byte[0], new byte[0]))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(updatedAdvert);

        mockMvc.perform(put("/adverts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateAdvertUseCase).updateAdvert(any(Advert.class));
    }


    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void deleteAdvert_shouldReturn204_whenAdvertDeleted() throws Exception {

        mockMvc.perform(delete("/adverts/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteAdvertUseCase).deleteAdvert(1L);
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void getAllAdverts_shouldReturn200WithAdverts_whenAdvertsFound() throws Exception {
        List<Advert> adverts = new ArrayList<>();
        adverts.add(Advert.builder()
                .id(1L)
                .price(1000)
                .description("Spacious 2-bedroom apartment for rent")
                .utilitiesIncluded(true)
                .creationDate(null)
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(Arrays.asList(new byte[0], new byte[0]))
                .build());
        adverts.add(Advert.builder()
                .id(2L)
                .price(1200)
                .description("Modern studio with a view")
                .utilitiesIncluded(false)
                .creationDate(null)
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(Arrays.asList(new byte[0], new byte[0]))
                .build());

        AdvertFilterCriteria filterCriteria = AdvertFilterCriteria.builder()
                .city(null)
                .minPrice(0)
                .maxPrice(0)
                .furnishingType(null)
                .pageNumber(0)
                .pageSize(10)
                .build();

        when(getAllAdvertsUseCase.getAllAdverts(any(AdvertFilterCriteria.class))).thenReturn(adverts);

        mockMvc.perform(get("/adverts")
                        .param("minPrice", String.valueOf(filterCriteria.getMinPrice()))
                        .param("maxPrice", String.valueOf(filterCriteria.getMaxPrice()))
                        .param("pageNumber", String.valueOf(filterCriteria.getPageNumber()))
                        .param("pageSize", String.valueOf(filterCriteria.getPageSize())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {
                             "adverts": [
                                 {
                                     "id": 1,
                                     "price": 1000.0,
                                     "description": "Spacious 2-bedroom apartment for rent",
                                     "property": {
                                         "id": null,
                                         "size": 0,
                                         "numberOfRooms": 0,
                                         "propertyType": null,
                                         "furnishingType": null,
                                         "address": {
                                             "id": null,
                                             "city": null,
                                             "street": null,
                                             "zipcode": null
                                         }
                                     },
                                     "creator": {
                                         "id": null,
                                         "name": null,
                                         "email": null,
                                         "description": null,
                                         "hashedPassword": null,
                                         "address": {
                                             "id": null,
                                             "city": null,
                                             "street": null,
                                             "zipcode": null
                                         },
                                         "gender": null,
                                         "birthDate": null,
                                         "profilePicture": null,
                                         "joinDate": null,
                                         "role": null,
                                         "status": null
                                     },
                                     "utilitiesIncluded": true,
                                     "availableFrom": null,
                                     "creationDate": null,
                                     "photos": [
                                         "",
                                         ""
                                     ]
                                 },
                                 {
                                     "id": 2,
                                     "price": 1200.0,
                                     "description": "Modern studio with a view",
                                     "property": {
                                         "id": null,
                                         "size": 0,
                                         "numberOfRooms": 0,
                                         "propertyType": null,
                                         "furnishingType": null,
                                         "address": {
                                             "id": null,
                                             "city": null,
                                             "street": null,
                                             "zipcode": null
                                         }
                                     },
                                     "creator": {
                                         "id": null,
                                         "name": null,
                                         "email": null,
                                         "description": null,
                                         "hashedPassword": null,
                                         "address": {
                                             "id": null,
                                             "city": null,
                                             "street": null,
                                             "zipcode": null
                                         },
                                         "gender": null,
                                         "birthDate": null,
                                         "profilePicture": null,
                                         "joinDate": null,
                                         "role": null,
                                         "status": null
                                     },
                                     "utilitiesIncluded": false,
                                     "availableFrom": null,
                                     "creationDate": null,
                                     "photos": [
                                         "",
                                         ""
                                     ]
                                 }
                            ]
                        }
            """));

        verify(getAllAdvertsUseCase).getAllAdverts(any(AdvertFilterCriteria.class) );
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void getAdvertsByLandlord_shouldReturn200WithAdverts_whenAdvertsFound() throws Exception {
        Long landlordId = 1L;
        List<Advert> mockAdverts = Arrays.asList(
                Advert.builder().build(),
                Advert.builder().build()
        );

        when(getAdvertsByLandlord.getAdvertsByLandlord(landlordId)).thenReturn(mockAdverts);

        mockMvc.perform(get("/adverts/user/{landlordId}", landlordId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(mockAdverts.size()));

        verify(getAdvertsByLandlord).getAdvertsByLandlord(landlordId);
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void getStatisticsByAdvertCity_shouldReturn200WithStatistics_whenStatisticsFound() throws Exception {
        String city = "TestCity";
        CityStatistics mockStatistics = CityStatistics.builder()
                .advertsCount(10)
                .averageAdvertPrice(1000.0)
                .averagePropertySize(150.0)
                .build();

        when(getCityStatisticsUseCase.getCityStatistics(city)).thenReturn(mockStatistics);

        mockMvc.perform(get("/adverts/statistics/{city}", city))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.advertsCount").value(mockStatistics.getAdvertsCount()))
                .andExpect(jsonPath("$.averageAdvertPrice").value(mockStatistics.getAverageAdvertPrice()))
                .andExpect(jsonPath("$.averagePropertySize").value(mockStatistics.getAveragePropertySize()));

        verify(getCityStatisticsUseCase).getCityStatistics(city);
    }
}