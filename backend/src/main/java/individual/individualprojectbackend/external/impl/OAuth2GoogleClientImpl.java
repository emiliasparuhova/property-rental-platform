package individual.individualprojectbackend.external.impl;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import individual.individualprojectbackend.external.OAuth2GoogleClient;
import individual.individualprojectbackend.external.dto.OAuth2GoogleTokenDTO;
import individual.individualprojectbackend.external.dto.OAuth2UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class OAuth2GoogleClientImpl implements  OAuth2GoogleClient {
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    public OAuth2GoogleClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public OAuth2GoogleTokenDTO exchangeCodeForToken(String code) {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", googleClientId);
        requestBody.add("client_secret", googleClientSecret);
        requestBody.add("code", code);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", googleRedirectUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenEndpoint, requestEntity, String.class);

        if (Objects.isNull(responseEntity.getBody())){
            return OAuth2GoogleTokenDTO.builder().build();
        }

        JsonObject jsonObject = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();

        return OAuth2GoogleTokenDTO.builder()
                .accessToken(jsonObject.get("access_token").toString())
                .build();
    }

    @Override
    public OAuth2UserDTO getUserProfile(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (Objects.isNull(responseEntity.getBody())){
            return OAuth2UserDTO.builder().build();
        }

        JsonObject jsonObject = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();

        return OAuth2UserDTO.builder()
                .id(jsonObject.get("sub").getAsString())
                .email(jsonObject.get("email").getAsString())
                .name(jsonObject.get("name").getAsString())
                .provider("google")
                .build();
    }
}
