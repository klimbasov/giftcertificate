package com.epam.esm.dao;

import com.epam.esm.dao.entity.Certificate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CertificateDao {

    Certificate create(Certificate certificate, Set<Integer> tagIds);

    Optional<Certificate> read(int id);

    List<Certificate> read(String name, String desc, String tag);

    void delete(int id);

    void update(Certificate certificate, Set<Integer> tagIds);
}
