package com.epam.esm.dao.mappers;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Entity;
import com.epam.esm.dao.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.constant.TableNames.Certificate.ID;
import static com.epam.esm.dao.constant.TableNames.Tag.*;

public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .name(rs.getString(NAME))
                .id(rs.getInt(ID))
                .build();
    }
}
