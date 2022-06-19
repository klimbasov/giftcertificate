package com.epam.esm.dao.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id = 0;

    @NonNull
    String name;
}