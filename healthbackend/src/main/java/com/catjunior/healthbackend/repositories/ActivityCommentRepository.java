package com.catjunior.healthbackend.repositories;

import com.catjunior.healthbackend.model.ActivityComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityCommentRepository extends CrudRepository<ActivityComment, Long> {
}
