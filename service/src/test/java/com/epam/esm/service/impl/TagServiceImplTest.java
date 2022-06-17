package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfig.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {

    @Autowired
    TagDao tagDao;

    TagService tagService;

    TagDto sample;

    @BeforeAll
    public void setup() {
        Tag tag1 = Tag.builder().id(1).name("name1").build();
        Tag tag3 = Tag.builder().id(3).name("name3").build();
        Tag tag4 = Tag.builder().id(4).name("name4").build();
        Tag createdTag = Tag.builder().id(5).name("new name").build();
        sample = new TagDto(0, createdTag.getName());

        Mockito.when(tagDao.read(1)).thenReturn(Optional.ofNullable(tag1));
        Mockito.when(tagDao.read(tag1.getName())).thenReturn(Arrays.asList(tag1));
        Mockito.when(tagDao.read(1)).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.read("name")).thenReturn(Arrays.asList(tag1, tag3, tag4));
        Mockito.when(tagDao.create(Mockito.any(Tag.class))).thenReturn(Optional.of(createdTag));
        Mockito.when(tagDao.readByCertificateId(1)).thenReturn(new HashSet<>(Arrays.asList(tag1, tag4)));
        tagService = new TagServiceImpl(tagDao);
    }

    @Test
    void add() {
        String expectedName = sample.getName();
        TagDto actual;

        actual = assertDoesNotThrow(() -> tagService.add(sample));
        assertEquals(expectedName, actual.getName());
        assertTrue(actual.getId() > 0);
    }

    @Test
    void addNull() {
        assertThrows(InvalidRequestException.class, () -> tagService.add(null));
    }

    @Test
    void addInvalidName() {
        TagDto dtoSmallName = new TagDto(1, "");
        TagDto dtoLargeName = new TagDto(1, "qwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwe");
        assertThrows(InvalidRequestException.class, () -> tagService.add(dtoSmallName));
        assertThrows(InvalidRequestException.class, () -> tagService.add(dtoLargeName));
    }

    @Test
    void getByExistingId() {
        int spottedId = 1;
        int expected = spottedId;
        int actual;

        actual = assertDoesNotThrow(() -> tagService.get(spottedId)).getId();
        assertEquals(expected, actual);
    }
}