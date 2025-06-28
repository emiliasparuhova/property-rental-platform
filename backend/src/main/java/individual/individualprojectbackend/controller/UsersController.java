package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.*;
import individual.individualprojectbackend.controller.converter.UserRequestsConverter;
import individual.individualprojectbackend.controller.dto.*;
import individual.individualprojectbackend.domain.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetLandlordResponseRate getLandlordResponseRate;

    private UserRequestsConverter userRequestsConverter;
    private GetUserIdFromTokenUseCase getUserIdFromTokenUseCase;
    private CompareUserIdWithTokenUseCase compareUserIdWithTokenUseCase;

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id){
        final GetUserRequest request = GetUserRequest.builder()
                .id(id)
                .build();

        final GetUserResponse response = GetUserResponse.builder()
                .user(getUserUseCase.getUser(request.getId()))
                .build();

        if (Objects.isNull(response.getUser())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response.getUser());
    }

    @GetMapping("/accessToken")
    public ResponseEntity<User> getUserByAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String accessToken){
        final Long userId = getUserIdFromTokenUseCase.getIdFromToken(accessToken);

        final GetUserResponse response = GetUserResponse.builder()
                .user(getUserUseCase.getUser(userId))
                .build();

        if (Objects.isNull(response.getUser())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response.getUser());
    }
    @PostMapping()
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        final User user = userRequestsConverter.convertCreateRequest(request);
        final String plainTextPassword = request.getPassword();

        final CreateUserResponse response = CreateUserResponse.builder()
                .id(createUserUseCase.createUser(user, plainTextPassword))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") final long id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) final String accessToken) {
        compareUserIdWithTokenUseCase.compareIdWithToken(id, accessToken);

        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") final long id,
                                           @RequestBody @Valid UpdateUserRequest request,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) final String accessToken){
        compareUserIdWithTokenUseCase.compareIdWithToken(id, accessToken);

        request.setId(id);
        final User user = userRequestsConverter.convertUpdateRequest(request);

        updateUserUseCase.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/landlord-response-rate/{id}")
    public ResponseEntity<Double> getLandlordResponseRate(@PathVariable("id") final long id){
        final Double responseRate = getLandlordResponseRate.getLandlordResponseRate(id);

        return ResponseEntity.ok().body(responseRate);
    }

}
