package com.epam.esm.dao.mappers;

import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Entity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.epam.esm.dao.constant.TableNames.Certificate.*;

public class CertificateRowMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder()
                .name(rs.getString(NAME))
                .createDate(rs.getDate(CREATE_DATE))
                .description(rs.getString(DESCRIPTION))
                .lastUpdateDate(rs.getDate(LAST_UPDATE_DATE))
                .duration(rs.getInt(DURATION))
                .price(rs.getFloat(PRICE))
                .id(rs.getInt(ID))
                .build();
    }
}
