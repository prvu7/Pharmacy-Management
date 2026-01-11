package com.mpp.pharmacy.External;

import com.mpp.pharmacy.DTO.OpenFdaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import java.net.URI;

@Service
public class FdaApiService {

    @Value("${app.fda.api-key}")
    private String apiKey;

    private final RestClient restClient;

    public FdaApiService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.fda.gov/drug/drugsfda.json")
                .build();
    }

    public boolean isDrugApproved(String brandName) {
        String searchQuery = String.format("openfda.brand_name:\"%s\"", brandName);

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.fda.gov/drug/drugsfda.json")
                .queryParam("search", searchQuery)
                .queryParam("api_key", apiKey)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return true;
                    } else if (response.getStatusCode().value() == 404) {
                        return false;
                    } else {
                        throw new ResourceNotFoundException("FDA API Error: " + response.getStatusCode());
                    }
                });
    }
}
