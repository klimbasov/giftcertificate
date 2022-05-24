package com.epam.esm.dao.seters;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

public class CertificateTagButchInsertPreparedStatementSetter implements BatchPreparedStatementSetter {
    Integer[] tagIds;
    int certificateId;

    public CertificateTagButchInsertPreparedStatementSetter(int certificateId, Set<Integer> tagIds){
        if(isNull(tagIds) || tagIds.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.certificateId = certificateId;
        this.tagIds = new Integer[tagIds.size()];
        tagIds.toArray(this.tagIds);
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setInt(1, certificateId);
        ps.setInt(2, tagIds[i]);
    }

    @Override
    public int getBatchSize() {
        return tagIds.length;
    }
}
