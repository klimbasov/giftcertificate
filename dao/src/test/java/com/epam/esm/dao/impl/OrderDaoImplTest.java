package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Order;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestDaoConfig.class)
@SpringBootApplication
@ActiveProfiles("test")
@Transactional
class OrderDaoImplTest {

    @Autowired
    private OrderDao orderDao;

    private final LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    private final Certificate certificateTemplate = Certificate.builder()
            .id(0)
            .createDate(time)
            .lastUpdateDate(time)
            .description("descr")
            .duration(10)
            .isSearchable(true).build();
    private final Order orderTemplate = Order.builder()
            .id(0)
            .cost(10.01)
            .timestamp(time).build();

    @PersistenceContext
    private EntityManager entityManager;

    @Nested
    class CreateTest {
        private final User user = new User(0, "name1", new HashSet<>());
        private final Certificate certificate = certificateTemplate.toBuilder().name("name1").build();
        private final Order expected = orderTemplate.toBuilder().user(user).certificate(certificate).build();

        @Test
        void create() {
            entityManager.persist(user);
            entityManager.persist(certificate);
            Optional<Order> optional = assertDoesNotThrow(() -> orderDao.create(expected));
            assertTrue(optional.isPresent());
            assertEquals(expected, optional.get());
        }
    }

    @Nested
    class ReadByIdTest {
        private final User user = new User(0, "name2", new HashSet<>());
        private final Certificate certificate = certificateTemplate.toBuilder().name("name2").build();
        private final Order expected = orderTemplate.toBuilder().user(user).certificate(certificate).build();
        private final long nonexistentId = 10000;

        @Test
        void readExistent() {
            entityManager.persist(user);
            entityManager.persist(certificate);
            entityManager.persist(expected);
            Optional<Order> optional = assertDoesNotThrow(() -> orderDao.read(expected.getId()));
            assertTrue(optional.isPresent());
            assertEquals(expected, optional.get());
        }

        @Test
        void readNonexistent() {
            Optional<Order> optional = assertDoesNotThrow(() -> orderDao.read(nonexistentId));
            assertFalse(optional.isPresent());
        }
    }

    @Nested
    class ReadAndCountByUserId {
        private final User user = new User(0, "name3", new HashSet<>());
        private final Certificate certificate = certificateTemplate.toBuilder().name("name4").build();
        private final List<Order> expected = Arrays.asList(orderTemplate.toBuilder().user(user).certificate(certificate).build(),
                orderTemplate.toBuilder().user(user).certificate(certificate).build(),
                orderTemplate.toBuilder().user(user).certificate(certificate).build()
        );
        private final long nonexistentId = 10000;

        @Test
        void readAndCountExistent() {
            entityManager.persist(user);
            entityManager.persist(certificate);
            for (Order order : expected) {
                entityManager.persist(order);
            }
            List<Order> actual = assertDoesNotThrow(() -> orderDao.read(0, 10, user.getId()));
            assertIterableEquals(expected, actual);
            assertEquals(actual.size(), assertDoesNotThrow(() -> orderDao.count(user.getId())));
        }

        @Test
        void readAndCountNonexistent() {
            List<Order> actual = assertDoesNotThrow(() -> orderDao.read(0, 10, nonexistentId));
            assertTrue(actual.isEmpty());
            assertEquals(0, assertDoesNotThrow(() -> orderDao.count(nonexistentId)));
        }
    }

    @Nested
    class ReadTest {
        private final User user = new User(0, "name4", new HashSet<>());
        private final Certificate certificate = certificateTemplate.toBuilder().name("name5").build();
        private final List<Order> expected = Arrays.asList(orderTemplate.toBuilder().user(user).certificate(certificate).build(),
                orderTemplate.toBuilder().user(user).certificate(certificate).build(),
                orderTemplate.toBuilder().user(user).certificate(certificate).build()
        );

        @Test
        void readAll() {
            entityManager.persist(user);
            entityManager.persist(certificate);
            for (Order order : expected) {
                entityManager.persist(order);
            }

            assertIterableEquals(expected, assertDoesNotThrow(() -> orderDao.read(0, 10)));
            assertEquals(2, assertDoesNotThrow(() -> orderDao.read(0, 2)).size());
            assertEquals(2, assertDoesNotThrow(() -> orderDao.read(1, 10)).size());
            assertEquals(0, assertDoesNotThrow(() -> orderDao.read(5, 10)).size());
            assertThrows(Exception.class, () -> orderDao.read(-1, 0));
            assertThrows(Exception.class, () -> orderDao.read(0, -1));
        }
    }

    @Nested
    class DeleteTest {
        private final User user = new User(0, "name5", new HashSet<>());
        private final Certificate certificate = certificateTemplate.toBuilder().name("name6").build();
        private final Order deletingEntity = orderTemplate.toBuilder().user(user).certificate(certificate).build();
        private final long nonexistentId = 100000;

        @Test
        void deleteExistent() {
            entityManager.persist(user);
            entityManager.persist(certificate);
            entityManager.persist(deletingEntity);
            assertEquals(1, assertDoesNotThrow(() -> orderDao.delete(deletingEntity.getId())));
            assertNotNull(entityManager.find(User.class, user.getId()));
            assertNotNull(entityManager.find(Certificate.class, certificate.getId()));
        }

        @Test
        void deleteNonexistent() {
            assertEquals(0, assertDoesNotThrow(() -> orderDao.delete(nonexistentId)));
        }
    }
}