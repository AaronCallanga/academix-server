package com.academix.academix.document.feedback.repository;

import com.academix.academix.document.feedback.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // _Id lets Spring Data know you want to match on the foreign key column (document_request_id)
    Optional<Feedback> findByDocumentRequest_Id(Long id);
    Page<Feedback> findByRating(int rating, Pageable pageable);
}
