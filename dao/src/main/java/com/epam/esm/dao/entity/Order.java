package com.epam.esm.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    User user;
}
