package com.catjunior.healthbackend.dto;

import com.catjunior.healthbackend.model.enums.FriendRequestStatus;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a friend request.
 */
@Data
public class FriendRequestDto {
    private FriendRequestStatus status;
}
