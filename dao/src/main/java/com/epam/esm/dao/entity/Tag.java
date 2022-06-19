package com.epam.esm.dao.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id = 0;

    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    String name;
}