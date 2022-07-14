package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestDaoConfig.class)
@SpringBootApplication
@ActiveProfiles("test")
@Transactional
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Nested
    class ReadById {
        private final long nonexistentId = 10000;
        private final User expected = new User(0, "name", new HashSet<>());

        @Test
        void readExistent() {
            entityManager.persist(expected);
            Optional<User> actual = assertDoesNotThrow(() -> userDao.read(expected.getId()));
            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
        }

        @Test
        void readNonexistent() {
            Optional<User> actual = assertDoesNotThrow(() -> userDao.read(nonexistentId));
            assertFalse(actual.isPresent());
        }
    }

    @Nested
    class ReadAndCountByName {
        private final List<User> expected = Arrays.asList(
                new User(0, "name1", new HashSet<>()),
                new User(0, "name2", new HashSet<>()),
                new User(0, "name3", new HashSet<>())
        );

        @Test
        void read() {
            for (User user : expected) {
                entityManager.persist(user);
            }

            assertIterableEquals(expected, assertDoesNotThrow(() -> userDao.read(0, 10, "name")));
            assertEquals(3, assertDoesNotThrow(() -> userDao.count("name")));
            assertEquals(2, assertDoesNotThrow(() -> userDao.read(1, 10, "name")).size());
            assertEquals(1, assertDoesNotThrow(() -> userDao.count(expected.get(0).getName())));
            Optional<User> optional = assertDoesNotThrow(() -> userDao.read(0, 1, expected.get(0).getName())).stream().findAny();
            assertTrue(optional.isPresent());
            assertEquals(expected.get(0), optional.get());
        }
    }
}