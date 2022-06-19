package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.mappers.TagRowMapper;
import com.epam.esm.dao.parametersources.TagParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.*;

import static com.epam.esm.dao.util.formatter.AlikeStringSqlFormatter.wrap;

@Repository
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public TagDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Optional<Tag> create(Tag tag) {
        entityManager.persist(tag);
        return Optional.of(tag);
    }

    @Override
    public Optional<Tag> read(long id) {
        return Optional.of(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> read(String name) {
        return new ArrayList<>();
    }

    @Override
    public Set<Tag> readByCertificateId(long certificateId) {
        return new HashSet<>();
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
