package com.zeniapp.segmentmiddleware.entities;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(64)", updatable = false, unique = true)
    @Setter
    @Getter
    @NonNull
    private String id;

    @Column(name = "email", columnDefinition = "VARCHAR(256)", updatable = true, unique = true)
    @Setter
    @Getter
    private String email;

    @Column(name = "username", columnDefinition = "VARCHAR(64)", updatable = true, unique = true)
    @Setter
    @Getter
    private String username;

    @Column(name = "first_name", columnDefinition = "VARCHAR(128)", updatable = true)
    @Setter
    @Getter
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(128)", updatable = true)
    @Setter
    @Getter
    private String lastName;
    
    @Column(name = "password", columnDefinition = "VARCHAR(2048)", updatable = true)
    @Setter
    @Getter
    private String password;

    @Column(name = "role", columnDefinition = "VARCHAR(48)", updatable = true)
    @Setter
    @Getter
    private String role;
    
    @Column(name = "is_confirmed", columnDefinition = "BIT", updatable = true)
    @Setter
    @Getter
    private Boolean isConfirmed = Boolean.FALSE;

    @Column(name = "is_blocked", columnDefinition = "BIT", updatable = true)
    @Setter
    @Getter
    private Boolean isBlocked = Boolean.FALSE;

    @Column(name = "is_deleted", columnDefinition = "BIT", updatable = true)
    @Setter
    @Getter
    private Boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "created_on", columnDefinition = "TIMESTAMP", updatable = false)
    @Setter
    @Getter
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", columnDefinition = "TIMESTAMP", updatable = true)
    @Setter
    @Getter
    private Timestamp updatedOn;
}
