package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.mappers.CertificateRowMapper;
import com.epam.esm.dao.parametersources.CertificateParameterSource;
import com.epam.esm.dao.seters.CertificateTagButchInsertPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

import static java.util.Objects.nonNull;

@Component
public class CertificateDaoImpl implements CertificateDao {

    JdbcTemplate template;

    @Autowired
    public CertificateDaoImpl(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Certificate create(Certificate certificate, Set<Integer> tagIds) {
        SimpleJdbcInsert certificateInsert = new SimpleJdbcInsert(template);
        certificateInsert.withTableName(TableNames.Certificate.TABLE_NAME)
                .usingGeneratedKeyColumns(TableNames.Certificate.ID)
                .usingColumns(TableNames.Certificate.NAME,
                        TableNames.Certificate.DESCRIPTION,
                        TableNames.Certificate.PRICE,
                        TableNames.Certificate.CREATE_DATE,
                        TableNames.Certificate.LAST_UPDATE_DATE,
                        TableNames.Certificate.DURATION);
        Number id = certificateInsert.executeAndReturnKey(new CertificateParameterSource(certificate));
        addCertificateTags(certificate.getId(), tagIds);
        return certificate.toBuilder().id(id.intValue()).build();
    }

    @Override
    public Optional<Certificate> read(int id) {
        Optional<Certificate> optionalEntity;
        optionalEntity = Optional.ofNullable(template.queryForObject(Queries.Certificate.SELECT_BY_ID, new CertificateRowMapper(), id));
        return optionalEntity;
    }

    @Override
    public Optional<List<Certificate>> read(String name, String desc, String tag) {
        List<Certificate> entityList = template.query(Queries.Certificate.SELECT, new CertificateRowMapper(), name, desc, tag);
        return Optional.of(entityList);
    }

    @Override
    public void delete(int id) {
        template.update(Queries.Certificate.DELETE, id);
    }

    @Override
    public void update(Certificate certificate, Set<Integer> tagIds) {
        template.update(Queries.Certificate.UPDATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                certificate.getDuration(),
                certificate.getId());

        template.update(Queries.CertificateTag.DELETE, certificate.getId());

        addCertificateTags(certificate.getId(), tagIds);
    }

    private void addCertificateTags(int id, Set<Integer> tagIds) {
        if(!tagIds.isEmpty() && nonNull(tagIds)){
            template.batchUpdate(Queries.CertificateTag.INSERT, new CertificateTagButchInsertPreparedStatementSetter(id, tagIds));
        }
    }
}
