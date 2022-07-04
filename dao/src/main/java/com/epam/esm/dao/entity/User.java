package com.epam.esm.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id = 0;

    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    String name = "";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<Order> orders = null;
}