package com.epam.esm.dao;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    Tag create(Tag tag) throws DaoException;

    Optional<Tag> read(int id);

    Optional<List<Tag>> read(String name);

    Optional<List<Tag>> readByCertificateId(Integer certificateId);

    void delete(int id);
}
