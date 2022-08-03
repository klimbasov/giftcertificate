package com.epam.esm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certificates")
public class    Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id = 0;

    @Column(name = "name", nullable = false, unique = true)
    String name = "";

    @Column(name = "description", nullable = false, length = 1000)
    String description = "";

    @Column(name = "price", nullable = false)
    double price = 0;

    @Column(name = "duration", nullable = false)
    int duration = 0;

    @Column(name = "create_date", nullable = false)
    LocalDateTime createDate = null;


    @Column(name = "last_update_date", nullable = false)
    LocalDateTime lastUpdateDate = null;

    @Column(name = "searchable", nullable = false)
    boolean isSearchable = true;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "certificate_tag",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<Tag> tags = null;
}
