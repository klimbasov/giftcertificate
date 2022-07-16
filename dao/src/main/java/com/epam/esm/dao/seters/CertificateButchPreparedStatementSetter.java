package com.epam.esm.dao.seters;

import com.epam.esm.dao.entity.Certificate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class CertificateButchPreparedStatementSetter implements BatchPreparedStatementSetter {
    List<Certificate> certificateList;

    public CertificateButchPreparedStatementSetter(final List<Certificate> certificateList) {
        this.certificateList = nonNull(certificateList) ? certificateList : new ArrayList<>();
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setString(1, certificateList.get(i).getName());
        ps.setString(2, certificateList.get(i).getDescription());
        ps.setFloat(3, certificateList.get(i).getPrice());
        ps.setTimestamp(4, Timestamp.valueOf(certificateList.get(i).getCreateDate()));
        ps.setTimestamp(5, Timestamp.valueOf(certificateList.get(i).getLastUpdateDate()));
        ps.setInt(6, certificateList.get(i).getDuration());
    }

    @Override
    public int getBatchSize() {
        return certificateList.size();
    }
}
