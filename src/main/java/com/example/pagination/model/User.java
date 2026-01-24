package com.example.pagination.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User domain model representing a user from the external API.
 * Using Java 21 Record for immutability and conciseness.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record User(
        Long id,
        String firstName,
        String lastName,
        Integer age,
        String email,
        String phone,
        String username,
        String birthDate,
        String image,
        String bloodGroup,
        Double height,
        Double weight,
        String eyeColor,
        Hair hair,
        String ip,
        Address address,
        String macAddress,
        String university,
        Bank bank,
        Company company,
        String ein,
        String ssn,
        String userAgent,
        Crypto crypto,
        String role) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Hair(String color, String type) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Address(
            String address,
            String city,
            String state,
            String stateCode,
            String postalCode,
            Coordinates coordinates,
            String country) {
        public record Coordinates(Double lat, Double lng) {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Bank(
            String cardExpire,
            String cardNumber,
            String cardType,
            String currency,
            String iban) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Company(
            String department,
            String name,
            String title,
            Address address) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Crypto(String coin, String wallet, String network) {
    }
}
