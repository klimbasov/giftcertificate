package com.epam.esm.dao.entity;

import com.epam.esm.dao.constant.TableNames;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "certificates")
public class Certificate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id = 0;

    @Column(name = TableNames.Certificate.NAME, nullable = false, unique = true)
    String name = "";

    @Column(name = "description", nullable = false, length = 1000)
    String description = "";

    @Column(name = "price", nullable = false)
    double price = 0;

    @Column(name = "duration", nullable = false)
    int duration = 0;

    @Column(name = "createDate", nullable = false)
    LocalDateTime createDate = null;

    @Column(name = "lastUpdateDate", nullable = false)
    LocalDateTime lastUpdateDate = null;

    @ManyToMany
    @JoinTable(name = "certificate_tag",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<Tag> tags = null;
}
