package individual.individualprojectbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import individual.individualprojectbackend.business.*;
import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.domain.enums.*;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUseCase createUserUseCase;
    @MockBean
    private GetUserUseCase getUserUseCase;
    @MockBean
    private UpdateUserUseCase updateUserUseCase;
    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    private User createUserForTest(Long userId) {
        return User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .address(Address.builder().city("Test City").street("Test Street").zipcode("Test Zipcode").build())
                .gender(Gender.MALE)
                .birthDate(null)
                .profilePicture(null)
                .joinDate(null)
                .role(UserRole.TENANT)
                .status(UserStatus.WORKING)
                .build();
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"TENANT"})
    void getUser_shouldReturn200WithUser_whenUserFound() throws Exception {
        User user = createUserForTest(1L);

        when(getUserUseCase.getUser(1L)).thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                               "id": 1,
                               "name": "Test User",
                               "email": "test@example.com",
                               "address": {
                                 "id": null,
                                 "city": "Test City",
                                 "street": "Test Street",
                                 "zipcode": "Test Zipcode"
                               },
                               "gender": "MALE",
                               "birthDate": null,
                               "profilePicture": null,
                               "joinDate": null,
                               "role": "TENANT",
                               "status": "WORKING"
                             }
                """));
        verify(getUserUseCase).getUser(1L);
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void createUser_shouldReturn201WithId_whenUserCreated() throws Exception {

        when(createUserUseCase.createUser(any(User.class), any(String.class))).thenReturn(1L);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                              "name": "Test User",
                              "email": "test@example.com",
                              "password": "password",
                              "role": "TENANT"
                        }
                        """)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "id": 1
                    }
                """));

        verify(createUserUseCase).createUser(any(User.class), any(String.class));
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"LANDLORD"})
    void updateUser_shouldReturn204_whenUserUpdated() throws Exception {
        User updatedUser = createUserForTest(1L);
        String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWlseWFzcGFydWhvdmFAZ21haWwuY29tIiwiaWF0IjoxNzA0OTEwNTQ2LCJleHAiOjE3MDU3NzQ1NDYsInJvbGUiOiJMQU5ETE9SRCIsInVzZXJJZCI6MX0.DIhcq-a7cnwEBTzlB2S3zA2-icvihgQgpab7XgeNPkQ";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header("Authorization", accessToken)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateUserUseCase).updateUser(any(User.class));
    }

    @Test
    @WithMockUser(username = "emily@fontys.nl", roles = {"TENANT"})
    void deleteUser_shouldReturn204_whenUserDeleted() throws Exception {
        Long userId = 1L;
        String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWlseWFzcGFydWhvdmFAZ21haWwuY29tIiwiaWF0IjoxNzA0OTEwNTQ2LCJleHAiOjE3MDU3NzQ1NDYsInJvbGUiOiJMQU5ETE9SRCIsInVzZXJJZCI6MX0.DIhcq-a7cnwEBTzlB2S3zA2-icvihgQgpab7XgeNPkQ";

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("Authorization", accessToken))
                .andExpect(status().isNoContent());

        verify(deleteUserUseCase).deleteUser(userId);
    }
}