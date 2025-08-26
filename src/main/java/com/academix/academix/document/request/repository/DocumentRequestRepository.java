package com.academix.academix.document.request.repository;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.DocumentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    Page<DocumentRequest> findByRequestedById(Long userId, Pageable pageable);
    Page<DocumentRequest> findByRequestedByEmail(String email, Pageable pageable);

    @Query("""
        SELECT dr 
        FROM DocumentRequest dr
        JOIN FETCH dr.requestedBy
        WHERE dr.status = 'RELEASED'
          AND dr.pickUpDate < :cutoffDate
          AND NOT EXISTS (
              SELECT f FROM Feedback f WHERE f.documentRequest = dr
      )
    """)
    List<DocumentRequest> findRequestCompletedWithoutFeedback(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("""
        SELECT dr 
        FROM DocumentRequest dr 
        WHERE dr.status = 'READY_FOR_PICKUP' 
        AND (dr.pickUpDate = :threeDaysLater OR dr.pickUpDate = :oneDayLater)
          """)
    List<DocumentRequest> findReadyForPickupByPickupDate(
            @Param("threeDaysLater") LocalDateTime threeDaysLater,
            @Param("oneDayLater") LocalDateTime oneDayLater);

    List<DocumentRequest> findByStatusAndRequestDate(DocumentStatus status, LocalDateTime requestDate);
    void deleteByStatusAndRequestDate(Set<DocumentStatus> status, LocalDateTime requestDate);
}
