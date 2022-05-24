package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.mappers.CertificateRowMapper;
import com.epam.esm.dao.mappers.TagRowMapper;
import com.epam.esm.dao.parametersources.CertificateParameterSource;
import com.epam.esm.dao.parametersources.TagParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {

    JdbcTemplate template;

    @Autowired
    public TagDaoImpl(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Tag create(Tag tag) throws DaoException {
        return read(tag.getName())
                .orElseGet(()-> {
                    Number id = new SimpleJdbcInsert(template)
                            .withTableName(TableNames.Tag.TABLE_NAME)
                            .usingGeneratedKeyColumns(TableNames.Tag.ID)
                            .usingColumns(TableNames.Tag.NAME)
                            .executeAndReturnKey(new TagParameterSource(tag));
                    return Arrays.asList(tag.toBuilder().id(id.intValue()).build());
                }).stream()
                .filter(tag1 -> tag.getName().equals(tag1.getName()))
                .findAny()
                .orElseThrow(()->new DaoException("Internal error."));
    }

    @Override
    public Optional<Tag> read(int id) {
        Tag tag = template.queryForObject(Queries.Tag.SELECT_BY_ID, new TagRowMapper(), id);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<List<Tag>> read(String name) {
        List<Tag> entityList = template.query(Queries.Tag.SELECT, new TagRowMapper(), name);
        return Optional.of(entityList);
    }

    @Override
    public Optional<List<Tag>> readByCertificateId(Integer certificateId) {
        List<Tag> entityList = template.query(Queries.Tag.SELECT_BY_CERTIFICATE_ID, new TagRowMapper(), certificateId);
        return Optional.of(entityList);
    }

    @Override
    public void delete(int id) {
        template.update(Queries.Tag.DELETE, id);
    }
}
