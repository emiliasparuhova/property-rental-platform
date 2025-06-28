package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.AuthenticateOAuthUserUseCase;
import individual.individualprojectbackend.business.AuthenticateUserUseCase;
import individual.individualprojectbackend.business.CreateOAuth2RequestUseCase;
import individual.individualprojectbackend.controller.dto.AuthenticateUserRequest;
import individual.individualprojectbackend.controller.dto.AuthenticateUserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final AuthenticateOAuthUserUseCase authenticateOAuthUserUseCase;
    private final CreateOAuth2RequestUseCase createOAuth2RequestUseCase;


    @PostMapping()
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(@RequestBody @Valid AuthenticateUserRequest request) {
        AuthenticateUserResponse response = AuthenticateUserResponse.builder()
                .accessToken(authenticateUserUseCase.authenticateUser(request.getEmail(), request.getPlainTextPassword()))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/oauth")
    public ResponseEntity<AuthenticateUserResponse> authenticateOAuthUser(@RequestBody String linkedAccountUserId) {
        AuthenticateUserResponse response = AuthenticateUserResponse.builder()
                .accessToken(authenticateOAuthUserUseCase.authenticateOAuthUser(linkedAccountUserId))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/google")
    public ResponseEntity<String> redirectToGoogle() {
        final OAuth2AuthorizationRequest authorizationRequest = createOAuth2RequestUseCase.createOAuth2Request("google");

        String authorizationRequestUri = authorizationRequest.getAuthorizationRequestUri();

        return ResponseEntity.ok().body(authorizationRequestUri);
    }

}
