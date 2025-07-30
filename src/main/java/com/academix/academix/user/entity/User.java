package com.academix.academix.user.entity;

import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.security.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {     // STUDENT, maybe refactor name, then create another entity ADMIN/Schools officials
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String lrn;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String contactNumber;
    private String password;
    private boolean isVerified;

    // Allows for navigation and querying of the related entities
    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL, orphanRemoval = true)    // non-owning side, does not contain foreign key     // default fetch is LAZY
    private List<DocumentRequest> requests = new ArrayList<>();     // mappedBy - indicates that the relationship is managed by other entity

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

/*
Even though User is the non-owning side, adding cascade = CascadeType.ALL makes it behave as the "parent" for business logic.
This means:
1. When you save a User, all requests will also be saved.
2. When you delete a User, all related DocumentRequests will be deleted if orphanRemoval = true.
 orphanRemoval = If child is removed from parent list, delete it from DB too

 Owning side = contaisn foreign key through @JoinColumn
 Non-owning side = just refer it using mappedBy
 */

/*
You want to remove a child from the parent, but the child is the owning side (i.e., it holds the foreign key).
So how can you safely delete it?

🔥 Here's the correct way:
✅ 1. Set up the relationship properly:
java
@Entity
public class User {
    @Id
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentRequest> requests = new ArrayList<>();

    public void removeRequest(DocumentRequest request) {
        requests.remove(request);         // remove from collection
        request.setUser(null);            // break the owning side
    }

    public void addRequest(DocumentRequest request) {
        requests.add(request);
        request.setUser(this);
    }
}
 */