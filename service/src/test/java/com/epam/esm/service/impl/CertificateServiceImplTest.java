package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.IllegalArgumentException;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfig.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateServiceImplTest {

    @Autowired
    CertificateDao certificateDao;

    @Autowired
    TagDao tagDao;

    CertificateService certificateService;

    CertificateDto sample;

    @BeforeEach
    void setUp() throws DaoException {
        configureTagDaoMock();
        configureCertificateDaoMock();
        sample = CertificateDto.builder().name("cert5").duration(10).description("cert5 descr")
                .price(4.00f)
                .duration(10)
                .createDate("2021-05-01T12:32:11")
                .lastUpdateDate("2021-05-01T12:32:11")
                .tags(Arrays.asList("holo", "lolo"))
                .build();
        certificateService = new CertificateServiceImpl(certificateDao, tagDao);
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
                .build();
        Certificate certificate2 = Certificate.builder()
                .id(2)
                .name("cert2")
                .description("cert2 descr")
                .price(4.00f)
                .duration(10)
                .createDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .lastUpdateDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .build();
        Certificate createdCertificate = Certificate.builder()
                .id(5)
                .name("cert5")
                .description("cert5 descr")
                .price(4.00f)
                .duration(10)
                .createDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .lastUpdateDate(LocalDateTime.parse("2021-05-01T12:32:11"))
                .build();
        List<Certificate> mockCertificates = Arrays.asList(certificate1, certificate2);
        Mockito.when(certificateDao.create(notNull(Certificate.class), notNull(Set.class))).thenAnswer(invocation -> {
            Certificate certificate = invocation.getArgumentAt(0, Certificate.class);
            Object[] tagSet = invocation.getArgumentAt(1, Set.class).toArray();
            if (tagSet.length > 0 && !(tagSet[0] instanceof Integer || Objects.isNull(certificate))) {
                throw new DaoException();
            } else {
                return createdCertificate;
            }
        });
        Mockito.when(certificateDao.read(any(String.class), any(String.class), any(String.class))).thenAnswer(invocation -> {
            String[] options = new String[]{invocation.getArgumentAt(0, String.class),
                    invocation.getArgumentAt(1, String.class),
                    invocation.getArgumentAt(2, String.class)};
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
        Mockito.when(certificateDao.read(any(Integer.class))).thenAnswer(invocation -> {
            Integer id = invocation.getArgumentAt(0, Integer.class);
            Certificate certificate = null;
            for (Certificate cert : mockCertificates) {
                if (cert.getId().equals(id)) {
                    certificate = cert;
                }
            }
            return Optional.ofNullable(certificate);
        });


        Mockito.doReturn(1).when(certificateDao).delete(or(eq(1), eq(2)));
        Mockito.doReturn(0).when(certificateDao).delete(not(or(eq(1), eq(2))));
        Mockito.doNothing().when(certificateDao).update(any(Certificate.class), any(Set.class));
    }

    private void configureTagDaoMock() {
        Tag tag1 = Tag.builder().id(1).name("name1").build();
        Tag tag3 = Tag.builder().id(3).name("name3").build();
        Tag tag4 = Tag.builder().id(4).name("name4").build();
        Tag createdTag = Tag.builder().id(5).name("new name").build();
        Mockito.when(tagDao.read(1)).thenReturn(Optional.ofNullable(tag1));
        Mockito.when(tagDao.read(tag1.getName())).thenReturn(Arrays.asList(tag1));
        Mockito.when(tagDao.read(1)).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.read("name")).thenReturn(Arrays.asList(tag1, tag3, tag4));
        Mockito.when(tagDao.create(any(Tag.class))).thenReturn(createdTag);
        Mockito.when(tagDao.readByCertificateId(1)).thenReturn(new HashSet<>(Arrays.asList(tag1, tag4)));
    }

    @Nested
    @DisplayName("Tests for method add")
    class AddTest {
        CertificateDto inconsistentDto = CertificateDto.builder().build();

        @Test
        @DisplayName("Normal behavior with consistent input")
        void addConsistent() {
            Integer actual;

            actual = assertDoesNotThrow(() -> certificateService.add(sample).getId());

            assertTrue(actual > 0);
        }

        @Test
        @DisplayName("Null parameter passing")
        void addNull() {
            assertThrows(IllegalArgumentException.class, () -> certificateService.add(null));
        }

        @Test
        @DisplayName("Inconsistent parameter passing")
        void addInconsistent() {
            assertThrows(IllegalArgumentException.class, () -> certificateService.add(inconsistentDto));
        }

    }

    @Nested
    @DisplayName("Tests for method put")
    class PutTest {

        @Test
        @DisplayName("Normal behavior with consistent input")
        void putConsistent() {
            CertificateDto consistentDto = sample.toBuilder().id(2).build();
            assertDoesNotThrow(() -> certificateService.put(consistentDto));
        }

        @Test
        @DisplayName("Null parameter passing")
        void putInconsistent() {
            CertificateDto inconsistentDto = sample;
            assertThrows(IllegalArgumentException.class, () -> certificateService.put(inconsistentDto));
        }

        @Test
        @DisplayName("Inconsistent parameter passing")
        void putDoesNotExisting() {
            CertificateDto doesNotExistDto = sample.toBuilder().id(3).build();
            assertThrows(NoSuchObjectException.class, () -> certificateService.put(doesNotExistDto));
        }

    }

    @Nested
    @DisplayName("Tests for method read by id")
    class ReadByIdTest {
        private final int existingId = 1;
        private final int nonexistentId = 4;
        private final int inconsistentId = -1;


        @Test
        @DisplayName("Existent id value passing")
        void readExisting() {
            assertDoesNotThrow(() -> certificateService.get(existingId));
        }

        @Test
        @DisplayName("Negative id value passing")
        void readInconsistent() {
            assertThrows(IllegalArgumentException.class, () -> certificateService.get(inconsistentId));
        }

        @Test
        @DisplayName("Nonexistent id value passing")
        void readDoesNonexistent() {
            assertThrows(NoSuchObjectException.class, () -> certificateService.get(nonexistentId));
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
            assertDoesNotThrow(() -> certificateService.get(optionsForExistent));
        }

        @Test
        @DisplayName("Inconsistent options passing")
        void readInconsistent() {
            assertDoesNotThrow(() -> certificateService.get(inconsistentOptions));
        }

        @Test
        @DisplayName("Null passing")
        void readNull() {
            assertThrows(IllegalArgumentException.class, () -> certificateService.get((SearchOptions) null));
        }

        @Test
        @DisplayName("Options for nonexistent record passing")
        void readNonexistent() {
            List<CertificateDto> actual = assertDoesNotThrow(() -> certificateService.get(optionsForNonexistent));
            assertTrue(actual.isEmpty());
        }
    }

    @Nested
    @DisplayName("Tests for method delete")
    class DeleteTest {
        private final int existingId = 1;
        private final int nonexistentId = 4;
        private final int inconsistentId = -1;


        @Test
        @DisplayName("Existent id value passing")
        void deleteExisting() {
            assertDoesNotThrow(() -> certificateService.delete(existingId));
        }

        @Test
        @DisplayName("Negative id value passing")
        void deleteInconsistent() {
            assertThrows(IllegalArgumentException.class, () -> certificateService.delete(inconsistentId));
        }

        @Test
        @DisplayName("Nonexistent id value passing")
        void deleteNonexistent() {
            assertThrows(NoSuchObjectException.class, () -> certificateService.delete(nonexistentId));
        }
    }
}