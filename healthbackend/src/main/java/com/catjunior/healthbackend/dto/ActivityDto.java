package com.catjunior.healthbackend.dto;

import com.catjunior.healthbackend.model.enums.ActivityType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Data Transfer Object (DTO) for representing an activity.
 */
@Data
public class ActivityDto {
    private Long id;
    private ActivityType type;
    private Date startDatetime;
    private Date endDatetime;
    private Double distance;
    private Double speed;
    private long time;
    private List<LocationDto> locations;
    private UserSearchDto user;
    private long likesCount;
    private boolean hasCurrentUserLiked;
    private Optional<List<ActivityCommentDto>> comments;
}
