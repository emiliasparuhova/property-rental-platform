package individual.individualprojectbackend.external.impl;

import individual.individualprojectbackend.external.GeocodingClient;
import individual.individualprojectbackend.external.dto.GeocodingDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GeocodingClientImpl implements GeocodingClient {

    private final RestTemplate restTemplate;
    private final String apiKey;

    private static final Logger log = LoggerFactory.getLogger(GeocodingClientImpl.class);

    public GeocodingClientImpl(RestTemplate restTemplate, @Value("${geocoding.api}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }
    @Override
    public GeocodingDTO getCoordinates(String address) {
        final String url = "https://maps.googleapis.com/maps/api/geocode/json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("address", URLEncoder.encode(address, StandardCharsets.UTF_8))
                .queryParam("key", apiKey);


        String requestUrl = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String jsonResponse = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class).getBody();

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray resultsArray = jsonObject.optJSONArray("results");

            if (resultsArray != null && !resultsArray.isEmpty()) {
                JSONObject firstResult = resultsArray.getJSONObject(0);
                JSONObject geometry = firstResult.optJSONObject("geometry");
                JSONObject location = geometry.optJSONObject("location");

                if (location != null) {
                    double latitude = location.optDouble("lat");
                    double longitude = location.optDouble("lng");

                    return GeocodingDTO.builder()
                            .latitude(latitude)
                            .longitude(longitude)
                            .build();
                }
            }
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
        }

        return GeocodingDTO.builder().build();
    }
}
