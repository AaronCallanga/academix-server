package com.academix.academix.document.entity;

import com.academix.academix.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String documentType; // F-137, Good Moral, etc.

    @Column(nullable = false)
    private String purpose; // Enrollment, transfer, job, etc.

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private LocalDateTime requestDate;

    private LocalDateTime pickUpDate;

    // Manages the relationship, persistenec, changes, and so on
    @ManyToOne(fetch = FetchType.LAZY)      // the owning side, owns the foreign key namely 'requested_by_id'
    @JoinColumn(name = "requested_by_id", nullable = false)     //usually, make the list(or many items) as the owning side
    private User requestedBy;

    //private List<String> remarks;     // additional notes, feedback, clarification etc. // linked via uni-directional, just fetch remark when returning requestdto
}
// maybe create a new entity for file upload of ID or authorization letter and linked it
