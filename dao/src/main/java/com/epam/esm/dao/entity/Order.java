package com.epam.esm.dao.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.boot.actuate.audit.listener.AuditListener;

import java.time.LocalDateTime;

@EntityListeners(AuditListener.class)
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "cost")
    private double cost;

    @NonNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    User user;

    @PrePersist
    private void onPrePersist(){
        timestamp = LocalDateTime.now();
    }
}
