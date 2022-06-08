package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagDao {
    Tag create(Tag tag) throws DaoException;

    Optional<Tag> read(int id);

    List<Tag> read(String name);

    Set<Tag> readByCertificateId(Integer certificateId);

    void delete(int id);
}
