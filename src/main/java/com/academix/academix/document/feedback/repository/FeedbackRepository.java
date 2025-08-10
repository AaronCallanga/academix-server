package com.academix.academix.document.feedback.repository;

import com.academix.academix.document.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
