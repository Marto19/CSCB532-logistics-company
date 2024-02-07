package com.logistics.logisticsCompany.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This class is a data holder used for registration requests.
 * It uses Lombok annotations for boilerplate code reduction.
 *
 * @Data - Generates getters and setters for all fields, a useful toString method, and hashCode and equals implementations that check all non-transient fields.
 * @Builder - Lets you automatically produce the code required to have your class be instantiable with code such as:
 *            Person.builder().name("Adam Savage").city("San Francisco").job("Mythbusters").job("Unchained Reaction").build();
 * @AllArgsConstructor - Generates a constructor with one parameter for each field in your class. Fields are assigned from parameters in the order they are declared.
 * @NoArgsConstructor - Generates a constructor with no parameters.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /**
     * The username of the user trying to register.
     * This field is a part of the registration request.
     */
    private String username;
    /**
     * The password of the user trying to register.
     * This field is a part of the registration request.
     */
    private String password;
}