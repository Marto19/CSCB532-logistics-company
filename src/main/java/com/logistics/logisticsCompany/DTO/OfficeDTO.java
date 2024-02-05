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
    
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public long getId() {
        return id;
    }
    
    public String getOfficeName() {
        return officeName;
    }
    
    public String getCity() {
        return city;
    }
    
    public int getPostcode() {
        return postcode;
    }
    
    public String getAddress() {
        return address;
    }
}
