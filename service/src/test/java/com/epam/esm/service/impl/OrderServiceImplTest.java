package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.impl.OrderDtoEntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceImplTest {

    @Mock
    OrderDao orderDao = Mockito.mock(OrderDaoImpl.class);
    @Mock
    OrderDtoEntityMapper mapper = Mockito.mock(OrderDtoEntityMapper.class);

    OrderServiceImpl orderService;

    @BeforeAll
    void setUp() {
        orderService = new OrderServiceImpl(orderDao, mapper);
        User user = new User(1, "labdab", new HashSet<>());
        Order order1 = new Order(1, 123.0, LocalDateTime.of(1, 1, 1, 1, 1, 1), new Certificate(), user);
        Order order2 = new Order(2, 123.0, LocalDateTime.of(1, 1, 1, 1, 1, 1), new Certificate(), user);
        Order order3 = new Order(3, 123.0, LocalDateTime.of(1, 1, 1, 1, 1, 1), new Certificate(), new User());
        List<Order> existingOrders = Arrays.asList(order1, order2, order3);
        user.setOrders(new HashSet<>(Arrays.asList(order1, order2)));

        Mockito.when(orderDao.create(notNull(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgumentAt(0, Order.class);
            order.setId(1);
            return Optional.of(order);
        });
        Mockito.when(orderDao.read(any(Long.class))).thenAnswer(invocation -> {
            Optional<Order> optional = Optional.empty();
            Long id = invocation.getArgumentAt(0, Long.class);
            for (Order order : existingOrders) {
                if (id.equals(order.getId())) {
                    optional = Optional.of(order);
                }
            }
            return optional;
        });
        Mockito.when(orderDao.read(any(Integer.class), any(Integer.class))).thenReturn(existingOrders);
        Mockito.when(orderDao.count()).thenReturn((long) existingOrders.size());
        Mockito.when(orderDao.read(any(Integer.class), any(Integer.class), any(Long.class))).thenReturn(existingOrders);
        Mockito.when(orderDao.count(any(Long.class))).thenReturn((long) existingOrders.size());
        Mockito.when(orderDao.delete(1)).thenReturn(1);
        Mockito.when(orderDao.delete(100)).thenReturn(0);

        Mockito.when(mapper.mapToEntity(notNull(OrderDto.class))).thenReturn(new Order());
        Mockito.when(mapper.mapToModel(notNull(Order.class))).thenReturn(new OrderDto());
    }

    @Nested
    @DisplayName("Testing create")
    class AddTest {

        OrderDto consistentDto = new OrderDto(0, "", 0, 1, 1);
        OrderDto inconsistentDto = new OrderDto(0, "", 0, 0, 1);

        @Test
        void addConsistent() {
            assertDoesNotThrow(() -> orderService.create(consistentDto));
        }

        @Test
        void addInconsistent() {
            assertThrows(InvalidRequestException.class, () -> orderService.create(inconsistentDto));
        }

        @Test
        void addNull() {
            assertThrows(InvalidRequestException.class, () -> orderService.create(null));
        }
    }

    @Nested
    @DisplayName("Testing read-by-id")
    class ReadById {
        long consistentId = 2;
        long nonexistentId = 100;
        long inconsistentId = 0;

        @Test
        void readByConsistentId() {
            assertDoesNotThrow(() -> orderService.read(consistentId));
        }

        @Test
        void readByNonexistentId() {
            assertThrows(NoSuchObjectException.class, () -> orderService.read(nonexistentId));
        }

        @Test
        void readByInconsistentId() {
            assertThrows(InvalidRequestException.class, () -> orderService.read(inconsistentId));
        }

        @Test
        void readByNullId() {
            assertThrows(InvalidRequestException.class, () -> orderService.read((Long) null));
        }
    }

    @Nested
    @DisplayName("Testing read-by-option")
    class ReadByOption {
        SearchOptions consistentOptions = SearchOptions.builder().pageNumber(1).build();
        SearchOptions inconsistentOptions = SearchOptions.builder().pageNumber(-5).build();

        @Test
        void readByConsistentOptions() {
            assertDoesNotThrow(() -> orderService.read(consistentOptions));
        }

        @Test
        void readByInconsistentOptions() {
            assertThrows(InvalidRequestException.class, () -> orderService.read(inconsistentOptions));
        }

        @Test
        void readByNullOptions() {
            assertThrows(InvalidRequestException.class, () -> orderService.read((SearchOptions) null));
        }
    }

    @Nested
    @DisplayName("Testing read-by-option-and-user-id")
    class ReadByOptionAndUserId {
        private final SearchOptions consistentOptions = SearchOptions.builder().pageNumber(1).build();
        private final SearchOptions inconsistentOptions = SearchOptions.builder().pageNumber(-5).build();
        private final long consistentId = 1;
        private final long inconsistentId = 0;

        @Test
        void readByConsistentParams() {
            assertDoesNotThrow(() -> orderService.read(consistentOptions, consistentId));
        }

        @Test
        void readByInconsistentParams() {
            assertThrows(InvalidRequestException.class, () -> orderService.read(inconsistentOptions, consistentId));
            assertThrows(InvalidRequestException.class, () -> orderService.read(consistentOptions, inconsistentId));
            assertThrows(InvalidRequestException.class, () -> orderService.read(inconsistentOptions, inconsistentId));
        }

        @Test
        void readByNullParams() {
            assertThrows(InvalidRequestException.class, () -> orderService.read(null, consistentId));
        }
    }

    @Nested
    @DisplayName("Testing deleting-by-id")
    class DeleteById {
        long existentId = 2;
        long nonexistentId = 100;
        long inconsistentId = 0;

        @Test
        void deleteByExistingId() {
            assertDoesNotThrow(() -> orderService.delete(existentId));
        }

        @Test
        void deleteByInconsistentId() {
            assertThrows(InvalidRequestException.class, () -> orderService.delete(inconsistentId));
        }

        @Test
        void deleteByNonexistentId() {
            assertDoesNotThrow(() -> orderService.delete(nonexistentId));
        }

        @Test
        void deleteByNullId() {
            assertThrows(InvalidRequestException.class, () -> orderService.delete( null));
        }
    }
}