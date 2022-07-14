package com.epam.esm.dao.impl;

import com.epam.esm.dao.config.TestDaoConfig;
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
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = TestDaoConfig.class)
@SpringBootApplication
class TagDaoImplTest {

    @Autowired
    TagDaoImpl tagDao;

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    @Nested
    @Transactional
    class CreateTest {
        private final Tag consistentEntity = new Tag(0, "random", new HashSet<>());

        @Test
        void createConsistent() {
            Optional<Tag> optional = assertDoesNotThrow(() -> tagDao.create(consistentEntity));
            assertTrue(optional.isPresent());
            Tag actual = optional.get();
            assertEquals(consistentEntity, actual);
        }
    }

    @Nested
    @Transactional
    class ReadByIdTest {
        private final Tag spottedEntity = new Tag(0, "random1", new HashSet<>());
        private final long nonexistentId = 10000;

        @Test
        void existentId() {
            entityManager.persist(spottedEntity);
            Optional<Tag> optional = tagDao.read(spottedEntity.getId());
            assertTrue(optional.isPresent());
            assertEquals(spottedEntity, optional.get());
        }

        @Test
        void readExistentId() {
            Optional<Tag> optional = assertDoesNotThrow(() -> tagDao.read(nonexistentId));
            assertFalse(optional.isPresent());
        }
    }

    @Nested
    @Transactional
    class ReadByNameTest {
        private final Tag spottedEntity = new Tag(0, "random2", new HashSet<>());
        private final String nonexistentName = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";

        @Test
        void readExistent() {
            entityManager.persist(spottedEntity);
            Optional<Tag> optional = assertDoesNotThrow(() -> tagDao.read(spottedEntity.getName(), 0, 1, true).stream().findAny());
            assertTrue(optional.isPresent());
            assertEquals(spottedEntity, optional.get());
        }

        @Test
        void readNonexistent() {
            Optional<Tag> optional = assertDoesNotThrow(() -> tagDao.read(nonexistentName, 0, 1, true).stream().findAny());
            assertFalse(optional.isPresent());
        }

    }

    @Nested
    @Transactional
    class DeleteTest {
        private final Tag spottedEntity = new Tag(0, "random2", new HashSet<>());
        private final long nonExistentId = 100000;
        private final int successReturnValue = 1;
        private final int faultReturnValue = 0;

        @Test
        void deleteExistent() {
            entityManager.persist(spottedEntity);
            Tag persistentEntity = entityManager.find(Tag.class, spottedEntity.getId());
            assertEquals(persistentEntity, spottedEntity);
            assertEquals(successReturnValue, assertDoesNotThrow(() -> tagDao.delete(spottedEntity.getId())));
            assertNull(entityManager.find(Tag.class, spottedEntity.getId()));
        }

        @Test
        void deleteNonExistent() {
            assertEquals(faultReturnValue, assertDoesNotThrow(() -> tagDao.delete(nonExistentId)));
        }
    }
}