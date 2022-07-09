package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.impl.CertificateDtoEntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateServiceImplTest {

    @Mock
    CertificateDao certificateDao = Mockito.mock(CertificateDaoImpl.class);

    @Mock
    TagDao tagDao = Mockito.mock(TagDaoImpl.class);

    CertificateService certificateService;

    CertificateDto sample;

    @BeforeEach
    void setUp() {
        configureTagDaoMock();
        configureCertificateDaoMock();
        sample = CertificateDto.builder().name("cert5").duration(10).description("cert5 descr")
                .price(4.00f)
                .duration(10)
                .createDate("2021-05-01T12:32:11")
                .lastUpdateDate("2021-05-01T12:32:11")
                .tags(Arrays.asList("lolo", "momo"))
                .build();
        certificateService = new CertificateServiceImpl(certificateDao, tagDao, new CertificateDtoEntityMapper());
    }

    private void configureCertificateDaoMock() {
        Certificate certificate1 = Certificate.builder()
                .id(1)
                .name("cert1")
                .description("cert1 descr")
                .price(4.00f)
                .duration(10)
                .createDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .lastUpdateDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .tags(new HashSet<>())
                .build();
        Certificate certificate2 = Certificate.builder()
                .id(2)
                .name("cert2")
                .description("cert2 descr")
                .price(4.00f)
                .duration(10)
                .createDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .lastUpdateDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .tags(new HashSet<>())
                .build();
        Certificate createdCertificate = Certificate.builder()
                .id(5)
                .name("cert5")
                .description("cert5 descr")
                .price(4.00f)
                .duration(10)
                .createDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .lastUpdateDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .tags(new HashSet<>())
                .build();
        List<Certificate> mockCertificates = Arrays.asList(certificate1, certificate2);
        Mockito.when(certificateDao.create(notNull(Certificate.class))).thenAnswer(invocation -> {
            Certificate certificate = invocation.getArgumentAt(0, Certificate.class);
            Object[] tagSet = certificate.getTags().toArray(new Tag[0]);
            Optional<Certificate> optional;
            if (tagSet.length > 0 && !(tagSet[0] instanceof Tag || Objects.isNull(certificate))) {
                optional = Optional.empty();
            } else {
                optional = Optional.of(createdCertificate);
            }
            return optional;
        });
        Mockito.when(certificateDao.read(any(String.class), any(String.class), any(String[].class), any(Integer.class), any(Integer.class), any(Boolean.class))).thenAnswer(invocation -> {
            String[] options = new String[]{
                    invocation.getArgumentAt(0, String.class),
                    invocation.getArgumentAt(1, String.class)
            };
            for (String str : options) {
                if (Objects.isNull(str)) {
                    return new LinkedList<>();
                }
            }
            List<Certificate> list = new LinkedList<>();
            for (Certificate cert : mockCertificates) {
                if (cert.getName().contains(options[0]) && cert.getDescription().contains(options[1])) {
                    list.add(cert);
                }
            }
            return list;
        });
        Mockito.when(certificateDao.count(any(String.class), any(String.class), any(String[].class))).thenAnswer(invocation -> {
            String[] options = new String[]{
                    invocation.getArgumentAt(0, String.class),
                    invocation.getArgumentAt(1, String.class)
            };
            for (String str : options) {
                if (Objects.isNull(str)) {
                    return 0;
                }
            }
            List<Certificate> list = new LinkedList<>();
            for (Certificate cert : mockCertificates) {
                if (cert.getName().contains(options[0]) && cert.getDescription().contains(options[1])) {
                    list.add(cert);
                }
            }
            return list.size();
        });
        Mockito.when(certificateDao.read(any(Long.class))).thenAnswer(invocation -> {
            Long id = invocation.getArgumentAt(0, Long.class);
            Certificate certificate = null;
            for (Certificate cert : mockCertificates) {
                if (id.equals(cert.getId())) {
                    certificate = cert;
                }
            }
            return Optional.ofNullable(certificate);
        });


        Mockito.doReturn(1).when(certificateDao).delete(1);
    }

    private void configureTagDaoMock() {
        Tag tag1 = new Tag(1, "name1", null);
        Tag tag3 = new Tag(3, "name3", null);
        Tag tag4 = new Tag(4, "name4", null);
        Tag createdTag = new Tag(5, "new name", null);
        Mockito.when(tagDao.read(tag1.getName(), 0, 20, true)).thenReturn(Arrays.asList(tag1));
        Mockito.when(tagDao.read(1)).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.read("name", 0, 20, true)).thenReturn(Arrays.asList(tag1, tag3, tag4));
        Mockito.when(tagDao.create(any(Tag.class))).thenReturn(Optional.of(createdTag));
        Mockito.when(tagDao.readMostUsedTagOfUserWithHighestOrderCost()).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.count("mane")).thenReturn((long) Arrays.asList(tag1, tag3, tag4).size());
        Mockito.when(tagDao.count(tag1.getName())).thenReturn(1L);
        Mockito.when(tagDao.delete(4)).thenReturn(0);
        Mockito.when(tagDao.delete(1)).thenReturn(1);
    }

    @Nested
    @DisplayName("Tests for method add")
    class AddTest {

        private final CertificateDto inconsistentDto = CertificateDto.builder().build();

        @Test
        @DisplayName("Normal behavior with consistent input")
        void addConsistent() {
            Long actual;

            actual = assertDoesNotThrow(() -> certificateService.create(sample).getId());

            assertTrue(actual > 0);
        }

        @Test
        @DisplayName("Null parameter passing")
        void addNull() {
            assertThrows(InvalidRequestException.class, () -> certificateService.create(null));
        }

        @Test
        @DisplayName("Inconsistent parameter passing")
        void addInconsistent() {
            assertThrows(InvalidRequestException.class, () -> certificateService.create(inconsistentDto));
        }

    }

    @Nested
    @DisplayName("Tests for method put")
    class PutTest {

        @Test
        @DisplayName("Normal behavior with consistent input")
        void putConsistent() {
            CertificateDto consistentDto = sample.toBuilder().id(2).build();
            assertDoesNotThrow(() -> certificateService.update(consistentDto));
        }

        @Test
        @DisplayName("Null parameter passing")
        void putInconsistent() {
            CertificateDto inconsistentDto = sample;
            assertThrows(InvalidRequestException.class, () -> certificateService.update(inconsistentDto));
        }

        @Test
        @DisplayName("Inconsistent parameter passing")
        void putDoesNotExisting() {
            CertificateDto doesNotExistDto = sample.toBuilder().id(3).build();
            assertThrows(NoSuchObjectException.class, () -> certificateService.update(doesNotExistDto));
        }

    }

    @Nested
    @DisplayName("Tests for method read by id")
    class ReadByIdTest {
        private final long existingId = 1;
        private final long nonexistentId = 4;
        private final long inconsistentId = -1;


        @Test
        @DisplayName("Existent id value passing")
        void readExisting() {
            assertDoesNotThrow(() -> certificateService.read(existingId));
        }

        @Test
        @DisplayName("Negative id value passing")
        void readInconsistent() {
            assertThrows(InvalidRequestException.class, () -> certificateService.read(inconsistentId));
        }

        @Test
        @DisplayName("Nonexistent id value passing")
        void readDoesNonexistent() {
            assertThrows(NoSuchObjectException.class, () -> certificateService.read(nonexistentId));
        }
    }

    @Nested
    @DisplayName("Tests for method read by options")
    class ReadByConditionTest {
        private final SearchOptions optionsForExistent = SearchOptions.builder()
                .sorting("incr")
                .subname("cert")
                .subdescription("cert")
                .build();
        private final SearchOptions optionsForNonexistent = SearchOptions.builder()
                .sorting("incr")
                .subname("certificate that dose not exist")
                .subdescription("cert")
                .build();
        private final SearchOptions inconsistentOptions = SearchOptions.builder()
                .sorting("incr")
                .subname(null)
                .subdescription(null)
                .build();


        @Test
        @DisplayName("Options for existing record passing")
        void readExisting() {
            assertDoesNotThrow(() -> certificateService.read(optionsForExistent, new String[0]));
        }

        @Test
        @DisplayName("Inconsistent options passing")
        void readInconsistent() {
            assertDoesNotThrow(() -> certificateService.read(inconsistentOptions, new String[0]));
        }

        @Test
        @DisplayName("Null passing")
        void readNull() {
            assertThrows(InvalidRequestException.class, () -> certificateService.read(null, new String[0]));
        }

        @Test
        @DisplayName("Options for nonexistent record passing")
        void readNonexistent() {
            assertThrows(NoSuchObjectException.class, () -> certificateService.read(optionsForNonexistent, new String[0]));
        }
    }

    @Nested
    @DisplayName("Tests for method delete")
    class DeleteTest {
        private final long existingId = 1;
        private final long nonexistentId = 4;
        private final long inconsistentId = -1;


        @Test
        @DisplayName("Existent id value passing")
        void deleteExisting() {
            assertDoesNotThrow(() -> certificateService.delete(existingId));
        }

        @Test
        @DisplayName("Negative id value passing")
        void deleteInconsistent() {
            assertThrows(InvalidRequestException.class, () -> certificateService.delete(inconsistentId));
        }

        @Test
        @DisplayName("Nonexistent id value passing")
        void deleteNonexistent() {
            assertThrows(NoSuchObjectException.class, () -> certificateService.delete(nonexistentId));
        }
    }
}