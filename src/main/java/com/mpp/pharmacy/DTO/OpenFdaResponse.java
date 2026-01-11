package com.mpp.pharmacy.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenFdaResponse(Meta meta, List<Result> results) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Meta(Results results) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Results(int total) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(String application_number, List<Product> products) {}
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Product(String brand_name, String marketing_status) {}
}
