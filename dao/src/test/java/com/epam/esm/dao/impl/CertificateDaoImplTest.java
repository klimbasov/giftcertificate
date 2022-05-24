package com.epam.esm.dao.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.constant.Queries;
import com.epam.esm.dao.constant.TableNames;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.mappers.CertificateRowMapper;
import com.epam.esm.dao.parametersources.CertificateParameterSource;
import com.epam.esm.dao.util.CertificateGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDaoConfig.class)
@ActiveProfiles("test")
@Sql(value = "/sql/create_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CertificateDaoImplTest {

    @Autowired
    CertificateDaoImpl certificateDao;

    @Autowired
    JdbcTemplate template;

    @Test
    void create() {
        Certificate expected = CertificateGenerator.generate();
        Certificate actual;

        int id = certificateDao.create(expected, new HashSet<>()).getId();
        expected = expected.toBuilder().id(id).build();
        actual = template.queryForObject(Queries.Certificate.SELECT_BY_ID, new CertificateRowMapper(), id);

        assertEquals(expected, actual, "stored object is not equal to the new one");
    }

    @Test
    void read() {
        Certificate expected = CertificateGenerator.generate();
        Certificate actual;

        Number id = insertAndReturnAutogeneratedId(expected, template);

        expected = expected.toBuilder().id(id.intValue()).build();
        actual = certificateDao.read(id.intValue()).get();

        assertEquals(expected, actual);
    }

    @Test
    void testReadByNameAndDescriptionAndTag() {
        List<Certificate> certificateList = new LinkedList<>();
        Certificate.CertificateBuilder builder = CertificateGenerator.generate().toBuilder();
        certificateList.add(builder.name("cert1").build());
        certificateList.add(builder.name("cert2").build());

        List<Certificate> expected;
        List<Certificate> actual;

        expected = certificateList.stream().map(certificate -> {
            int id = insertAndReturnAutogeneratedId(certificate, template).intValue();
            return certificate.toBuilder().id(id).build();
        }).collect(Collectors.toList());

        actual = certificateDao.read("%cer%", "%%", "%%").get();

        assertIterableEquals(expected, actual);
    }

    @Test
    void delete() {
        Certificate certificate = CertificateGenerator.generate();

        int id = insertAndReturnAutogeneratedId(certificate, template).intValue();
        certificateDao.delete(id);

        assertNull(getCertificateById(id));
    }

    @Test
    void update() {
        String newName = "newName";
        Certificate certificate = CertificateGenerator.generate();
        Certificate updatedCertificate;

        Certificate expected;
        Certificate actual;

        int id = insertAndReturnAutogeneratedId(certificate, template).intValue();
        updatedCertificate = certificate.toBuilder().id(id).name(newName).build();
        certificateDao.update(updatedCertificate, new HashSet<>());

        expected = updatedCertificate;
        actual = getCertificateById(id);

        assertEquals(expected, actual);
    }

    private Number insertAndReturnAutogeneratedId(Certificate certificate, JdbcTemplate template) {
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
        return id;
    }

    private Certificate getCertificateById(int id) {
        Certificate certificate = null;
        List<Certificate> searchedCertificates = template.query(Queries.Certificate.SELECT_BY_ID, new CertificateRowMapper(), id);

        if(!searchedCertificates.isEmpty()){
            certificate = searchedCertificates.get(0);
        }
        return certificate;
    }
}