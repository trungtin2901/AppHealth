package com.catjunior.healthbackend.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing user data.
 */
@Data
public class UserSearchDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
}
