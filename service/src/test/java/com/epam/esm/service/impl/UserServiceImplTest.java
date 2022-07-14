package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.impl.UserDtoEntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    @Mock
    private final UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);

    private UserServiceImpl userService;

    @BeforeAll
    void setUp() {
        userService = new UserServiceImpl(userDao, new UserDtoEntityMapper());
    }

    @Nested
    @DisplayName("Testing read-by-id")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RedaByIdTest {
        private final long existentId = 1;
        private final long nonexistentId = 100;
        private final long inconsistentId = -1;

        @BeforeAll
        void setup() {
            Mockito.reset(userDao);
            User user1 = new User(1, "labdab", new HashSet<>());
            Mockito.when(userDao.read(any(Long.class))).thenAnswer(invocation -> {
                long id = invocation.getArgumentAt(0, Long.class);
                if (id == 1) {
                    return Optional.of(user1);
                } else {
                    return Optional.empty();
                }
            });
        }

        @Test
        void readExistent() {
            assertDoesNotThrow(() -> userService.read(existentId));
        }

        @Test
        void readNonExistent() {
            assertThrows(NoSuchObjectException.class, () -> userService.read(nonexistentId));
        }

        @Test
        void readInconsistent() {
            assertThrows(InvalidRequestException.class, () -> userService.read(inconsistentId));
        }
    }

    @Nested
    @DisplayName("Testing read-by-option")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ReadByOptionTest {
        SearchOptions validOptions = SearchOptions.builder().pageNumber(1).build();
        SearchOptions invalidOptions = SearchOptions.builder().pageNumber(-1).build();

        @BeforeAll
        void setup() {
            Mockito.reset(userDao);
            User user = new User(1, "user", new HashSet<>());
            Mockito.when(userDao.read(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(Arrays.asList(user));
            Mockito.when(userDao.count(any(String.class))).thenReturn(1L);
        }

        @Test
        void readValid() {
            assertDoesNotThrow(() -> userService.read(validOptions));
        }

        @Test
        void readInvalid() {
            assertThrows(InvalidRequestException.class, () -> userService.read(invalidOptions));
        }

    }
}