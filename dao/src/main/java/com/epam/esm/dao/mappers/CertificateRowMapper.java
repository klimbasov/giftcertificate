package com.epam.esm.dao.mappers;

import com.epam.esm.dao.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.constant.TableNames.Certificate.*;

public class CertificateRowMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder()
                .name(rs.getString(NAME))
                .createDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime())
                .description(rs.getString(DESCRIPTION))
                .duration(rs.getInt(DURATION))
                .price(rs.getFloat(PRICE))
                .id(rs.getInt(ID))
                .build();
    }
}
