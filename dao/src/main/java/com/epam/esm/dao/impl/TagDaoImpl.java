package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.mappers.TagRowMapper;
import com.epam.esm.dao.parametersources.TagParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.dao.util.formatter.AlikeStringSqlFormatter.wrap;

@Repository
public class TagDaoImpl implements TagDao {

    JdbcTemplate template;

    @Autowired
    public TagDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Tag create(Tag tag) {
        int id = new SimpleJdbcInsert(template)
                .withTableName(TableNames.Tag.TABLE_NAME)
                .usingGeneratedKeyColumns(TableNames.Tag.ID)
                .usingColumns(TableNames.Tag.NAME)
                .executeAndReturnKey(new TagParameterSource(tag)).intValue();
        return tag.toBuilder().id(id).build();
    }

    @Override
    public Optional<Tag> read(int id) {
        Tag tag = template.queryForObject(Queries.Tag.SELECT_BY_ID, new TagRowMapper(), id);
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> read(String name) {
        return template.query(Queries.Tag.SELECT, new TagRowMapper(), wrap(name));
    }

    @Override
    public Set<Tag> readByCertificateId(Integer certificateId) {
        return new HashSet<>(template.query(Queries.Tag.SELECT_BY_CERTIFICATE_ID, new TagRowMapper(), certificateId));
    }

    @Override
    public int delete(int id) {
        return template.update(Queries.Tag.DELETE, id);
    }
}
