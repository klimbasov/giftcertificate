package com.epam.esm.dao.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = TestDaoConfig.class)
@SpringBootApplication
@Transactional
class CertificateDaoImplTest {

    private final Certificate creatingEntity = new Certificate(0,
            "",
            "descr1",
            10.01,
            100,
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
            true,
            new HashSet<>()
    );
    @Autowired
    CertificateDaoImpl certificateDao;
    @PersistenceContext
    @Autowired
    EntityManager entityManager;

    @Nested
    class CreateTest {
        private final Tag tag = new Tag(0, "tag1", new HashSet<>());
        private final Certificate expected = creatingEntity.toBuilder().name("name1").tags(new HashSet<>(Arrays.asList(tag))).build();

        @Test
        void create() {
            assertDoesNotThrow(() -> certificateDao.create(expected));
            Certificate actual = entityManager.find(Certificate.class, expected.getId());
            assertEquals(actual, expected);
        }
    }

    @Nested
    class ReadByIdTest {
        private final Certificate expected = creatingEntity.toBuilder().name("name2").build();
        private final long nonexistentId = 100000;

        @Test
        void readExistent() {
            entityManager.persist(expected);
            Optional<Certificate> optional = assertDoesNotThrow(() -> certificateDao.read(expected.getId()));
            assertTrue(optional.isPresent());
            Certificate actual = optional.get();
            assertEquals(actual, expected);
        }

        @Test
        void readNonexistent() {
            Optional<Certificate> optional = assertDoesNotThrow(() -> certificateDao.read(nonexistentId));
            assertFalse(optional.isPresent());
        }

    }

    @Nested
    class ReadByOptionsTest {
        private final Tag tag = new Tag(0, "cert1tag1", new HashSet<>());

        private final Certificate expected = creatingEntity.toBuilder().tags(new HashSet<>(Arrays.asList(tag))).name("cert 3").build();
        private final String nonexistentName = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        private final String nonexistentDescription = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";

        @Test
        void readExistent() {
            entityManager.persist(tag);
            entityManager.persist(expected);
            Optional<Certificate> actual = assertDoesNotThrow(() -> certificateDao.read(expected.getName(), "", new String[0], 0, 1, true).stream().findAny());
            assertTrue(actual.isPresent());
            assertEquals(actual.get(), expected);
            actual = assertDoesNotThrow(() -> certificateDao.read("", expected.getDescription(), new String[0], 0, 1, true).stream().findAny());
            assertTrue(actual.isPresent());
            assertEquals(actual.get(), expected);
            actual = assertDoesNotThrow(() -> certificateDao.read("", "", new String[]{tag.getName()}, 0, 1, true).stream().findAny());
            assertTrue(actual.isPresent());
            assertEquals(actual.get(), expected);
        }

        @Test
        void readNonexistent() {
            entityManager.persist(tag);
            entityManager.persist(expected);
            Optional<Certificate> actual = assertDoesNotThrow(() -> certificateDao.read(nonexistentName, "", new String[0], 0, 1, true).stream().findAny());
            assertFalse(actual.isPresent());
            actual = assertDoesNotThrow(() -> certificateDao.read("", nonexistentDescription, new String[0], 0, 1, true).stream().findAny());
            assertFalse(actual.isPresent());
            actual = assertDoesNotThrow(() -> certificateDao.read("", "", new String[]{""}, 0, 1, true).stream().findAny());
            assertFalse(actual.isPresent());
        }
    }

    @Nested
    class UpdateTest {
        private final Certificate existedEntity = creatingEntity.toBuilder().name("name4").build();
        private final Certificate nonexistentEntity = creatingEntity.toBuilder().id(10000).name("name5").build();

        @Test
        void updateExistent() {
            entityManager.persist(existedEntity);
            existedEntity.setName("updatedName");
            assertDoesNotThrow(() -> certificateDao.update(existedEntity));
            Certificate actual = entityManager.find(Certificate.class, existedEntity.getId());
            assertEquals(actual, existedEntity);
        }

        @Test
        void updateNonexistent() {
            assertDoesNotThrow(() -> certificateDao.update(nonexistentEntity));
        }
    }

    @Nested
    class DeleteTest {
        private final Tag tag = new Tag(0, "tag6", new HashSet<>());
        private final Certificate existedEntity = creatingEntity.toBuilder().tags(new HashSet<>(Arrays.asList(tag))).name("name6").build();
        private final long nonexistentId = 10000;

        @Test
        void deleteExistent() {
            int successRetVal = 1;
            entityManager.persist(existedEntity);
            assertEquals(successRetVal, assertDoesNotThrow(() -> certificateDao.delete(existedEntity.getId())));
        }

        @Test
        void deleteNonexistent() {
            int failRetVal = 0;
            assertEquals(failRetVal, assertDoesNotThrow(() -> certificateDao.delete(nonexistentId)));
        }
    }
}