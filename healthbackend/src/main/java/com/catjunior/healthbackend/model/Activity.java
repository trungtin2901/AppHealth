package com.catjunior.healthbackend.model;

import com.catjunior.healthbackend.model.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
public class Activity extends AbstractEntity {

    /**
     * The user associated with the activity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The type of activity.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public ActivityType type;

    /**
     * The start datetime of the activity.
     */
    @Column(name = "startDatetime")
    public Date startDatetime;

    /**
     * The end datetime of the activity.
     */
    @Column(name = "endDatetime")
    public Date endDatetime;

    /**
     * The distance covered during the activity.
     */
    @Column(name = "globalDistance")
    public Double distance;

    /**
     * The speed achieved during the activity.
     */
    @Column(name = "speed")
    public Double speed;

    /**
     * The locations associated with the activity.
     */
    @OneToMany(targetEntity = Location.class, orphanRemoval = true, mappedBy = "activity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Location> locations;

    /**
     * The comments associated with the activity.
     */
    @OneToMany(targetEntity = ActivityComment.class, orphanRemoval = true, mappedBy = "activity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("createdAt ASC")
    private List<ActivityComment> comments;

    /**
     * The list of likes associated with the user.
     */
    @OneToMany(targetEntity = ActivityLike.class, mappedBy = "activity", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ActivityLike> likes;

}