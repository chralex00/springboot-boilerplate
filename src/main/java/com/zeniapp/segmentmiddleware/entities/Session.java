package com.zeniapp.segmentmiddleware.entities;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(64)", updatable = true)
    @Setter
    @Getter
    private String id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @Getter @Setter
    private Account account;

    @Column(name = "api_counter", columnDefinition = "INT", updatable = true)
    @Setter
    @Getter
    private Integer apiCounter;

    @CreationTimestamp
    @Column(name = "created_on", columnDefinition = "TIMESTAMP", updatable = false)
    @Setter
    @Getter
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name = "last_activity_on", columnDefinition = "TIMESTAMP", updatable = true)
    @Setter
    @Getter
    private Timestamp lastActivityOn;
}
