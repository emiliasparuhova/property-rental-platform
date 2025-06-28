package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.GetGoogleTokenByCodeUseCase;
import individual.individualprojectbackend.business.GetGoogleUserUseCase;
import individual.individualprojectbackend.domain.ExternalAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@AllArgsConstructor
public class OAuth2Controller {
    private GetGoogleTokenByCodeUseCase getGoogleTokenByCodeUseCase;
    private GetGoogleUserUseCase getGoogleUserUseCase;

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<ExternalAccount> handleGoogleCallback(@RequestParam("code") final String code) {

        final String accessToken = getGoogleTokenByCodeUseCase.getTokenByCode(code);
        final ExternalAccount externalAccount = getGoogleUserUseCase.getGoogleUser(accessToken);

        String redirectUrl = "http://localhost:5173/oauth-login?" +
                "id=" + URLEncoder.encode(externalAccount.getId(), StandardCharsets.UTF_8) +
                "&name=" + URLEncoder.encode(externalAccount.getName(), StandardCharsets.UTF_8) +
                "&email=" + URLEncoder.encode(externalAccount.getEmail(), StandardCharsets.UTF_8) +
                "&provider=" + URLEncoder.encode(externalAccount.getProvider(), StandardCharsets.UTF_8);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);    }

}
