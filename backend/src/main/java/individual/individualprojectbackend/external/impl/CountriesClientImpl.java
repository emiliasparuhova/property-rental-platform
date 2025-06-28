package individual.individualprojectbackend.external.impl;

import individual.individualprojectbackend.external.CountriesClient;
import individual.individualprojectbackend.external.dto.CityDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CountriesClientImpl implements CountriesClient {
    private final RestTemplate restTemplate;
    private final String apiKey;

    public CountriesClientImpl(RestTemplate restTemplate, @Value("${countries.api}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Override
    public CityDTO[] getCitiesByCountryCode(String countryCode) {
        final String baseUrl = "https://api.countrystatecity.in";
        String url = baseUrl + String.format("/v1/countries/%s/cities", countryCode);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CSCAPI-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, CityDTO[].class).getBody();
    }
}
