package com.epam.esm.dao.entity;

import lombok.*;
import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditListener.class)
@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    User user;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "cost", nullable = false)
    private double cost;
    @NonNull
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

    @PrePersist
    private void onPrePersist() {
        timestamp = LocalDateTime.now();
    }
}
