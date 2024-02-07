package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The OfficeDTO class is used to represent an office data transfer object.
 * It contains the id, office name, city, postcode, and address of the office.
 */
public class OfficeDTO {

    /**
     * The id of the office.
     * It is a unique identifier for the office.
     */
    @JsonProperty("id")
    private long id;

    /**
     * The name of the office.
     */
    @JsonProperty("officeName")
    private String officeName;

    /**
     * The city of the office.
     */
    @JsonProperty("city")
    private String city;

    /**
     * The postcode of the office.
     */
    @JsonProperty("postcode")
    private int postcode;

    /**
     * The address of the office.
     */
    @JsonProperty("address")
    private String address;

    @JsonProperty("companyId")
    private String companyId;
    
    /**
     * Default constructor for the {@code OfficeDTO} class.
     * It is used to create an empty office data transfer object.
     * This constructor is used by the Jackson library to create an empty office data transfer object during deserialization.
     *
     */
    // Ensure you have a no-argument constructor
    public OfficeDTO() {
    }

    /**
     * Constructs an OfficeDTO with the specified id, office name, city, postcode, and address.
     * @param id the id
     * @param officeName the office name
     * @param city the city
     * @param postcode the postcode
     * @param address the address
     */
    public OfficeDTO(long id, String officeName, String city, int postcode, String address, String companyName) {
        this.id = id;
        this.officeName = officeName;
        this.city = city;
        this.postcode = postcode;
        this.address = address;
        this.companyId = companyName;
    }

    // Getters and setters
    
    
    public String getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    
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
