package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfficeDTO {
    @JsonProperty("id")
    private long id;

    @JsonProperty("officeName")
    private String officeName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postcode")
    private int postcode;

    @JsonProperty("address")
    private String address;

    // Constructors, getters, and setters

    // Ensure you have a no-argument constructor
    public OfficeDTO() {
    }

    public OfficeDTO(long id, String officeName, String city, int postcode, String address) {
        this.id = id;
        this.officeName = officeName;
        this.city = city;
        this.postcode = postcode;
        this.address = address;
    }

    // Getters and setters
    // ...
}
