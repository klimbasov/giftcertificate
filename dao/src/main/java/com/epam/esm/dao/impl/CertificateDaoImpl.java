package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.mappers.CertificateRowMapper;
import com.epam.esm.dao.parametersources.CertificateParameterSource;
import com.epam.esm.dao.seters.CertificateTagButchInsertPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.dao.util.formatter.AlikeStringSqlFormatter.wrap;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Repository
public class CertificateDaoImpl implements CertificateDao {

    private final JdbcTemplate template;

    @Autowired
    public CertificateDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Certificate> create(Certificate certificate, Set<Integer> tagIds){
        SimpleJdbcInsert certificateInsert = new SimpleJdbcInsert(template);
        certificateInsert.withTableName(TableNames.Certificate.TABLE_NAME)
                .usingGeneratedKeyColumns(TableNames.Certificate.ID)
                .usingColumns(TableNames.Certificate.NAME,
                        TableNames.Certificate.DESCRIPTION,
                        TableNames.Certificate.PRICE,
                        TableNames.Certificate.CREATE_DATE,
                        TableNames.Certificate.LAST_UPDATE_DATE,
                        TableNames.Certificate.DURATION);
        Optional<Integer> optionalId = getExecuteAdnGetGeneratedId(certificate, certificateInsert);
        return getOptionalResultEntity(certificate, tagIds, optionalId);
    }

    private Optional<Certificate> getOptionalResultEntity(Certificate certificate, Set<Integer> tagIds, Optional<Integer> optionalId) {
        Optional<Certificate> optional = Optional.empty();
        if(optionalId.isPresent()){
            Integer id = optionalId.get();
            addCertificateTags(id, tagIds);
            optional = Optional.of(certificate.toBuilder().id(id).build());
        }
        return optional;
    }

    private Optional<Integer> getExecuteAdnGetGeneratedId(Certificate certificate, SimpleJdbcInsert certificateInsert) {
        Optional<Integer> id;
        try{
            id = Optional.of(certificateInsert.executeAndReturnKey(new CertificateParameterSource(certificate)).intValue());
        }catch (DuplicateKeyException ignored){
            id = Optional.empty();
        }
        return id;
    }

    @Override
    public Optional<Certificate> read(int id) {
        Optional<Certificate> optionalEntity;
        optionalEntity = template.query(Queries.Certificate.SELECT_BY_ID, new CertificateRowMapper(), id).stream().findAny();
        return optionalEntity;
    }

    @Override
    public List<Certificate> read(String name, String desc, String tag) {
        return template.query(Queries.Certificate.SELECT, new CertificateRowMapper(), wrap(name), wrap(desc), wrap(tag));
    }

    @Override
    public int delete(int id) {
        return template.update(Queries.Certificate.DELETE, id);
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
        if (isNotEmpty(tagIds)) {
            template.batchUpdate(Queries.CertificateTag.INSERT, new CertificateTagButchInsertPreparedStatementSetter(id, tagIds));
        }
    }
}
