package com.catjunior.healthbackend.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a refresh token.
 */
@Data
public class RefreshTokenDto {
    private String token;
}
