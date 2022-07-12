package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import com.epam.esm.service.util.mapper.impl.TagDtoEntityMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {

    @Mock
    TagDao tagDao = Mockito.mock(TagDaoImpl.class);

    TagService tagService;

    TagDto sample;

    @BeforeAll
    public void setup() {
        configureTagDaoMock();
        tagService = new TagServiceImpl(tagDao, new TagDtoEntityMapper());
    }

    private void configureTagDaoMock() {
        Tag tag1 = new Tag(1, "name1", null);
        Tag tag3 = new Tag(3, "name3", null);
        Tag tag4 = new Tag(4, "name4", null);
        Tag createdTag = new Tag(5, "new name", null);
        sample = new TagDto(0, createdTag.getName());

        Mockito.when(tagDao.read(1)).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.read(tag1.getName(), 0, 20, true)).thenReturn(singletonList(tag1));
        Mockito.when(tagDao.read(1)).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.read("name", 0, 20, true)).thenReturn(Arrays.asList(tag1, tag3, tag4));
        Mockito.when(tagDao.create(any(Tag.class))).thenReturn(Optional.of(createdTag));
        Mockito.when(tagDao.readMostUsedTagOfUserWithHighestOrderCost()).thenReturn(Optional.of(tag1));
        Mockito.when(tagDao.count("mane")).thenReturn((long) Arrays.asList(tag1, tag3, tag4).size());
        Mockito.when(tagDao.count(tag1.getName())).thenReturn(1L);
    }

    @Test
    void add() {
        String expectedName = sample.getName();
        TagDto actual;

        actual = assertDoesNotThrow(() -> tagService.create(sample));
        assertEquals(expectedName, actual.getName());
        assertTrue(actual.getId() > 0);
    }

    @Test
    void addNull() {
        assertThrows(InvalidRequestException.class, () -> tagService.create(null));
    }

    @Test
    void addInvalidName() {
        TagDto dtoSmallName = new TagDto(1, "");
        TagDto dtoLargeName = new TagDto(1, "qwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwe");
        assertThrows(InvalidRequestException.class, () -> tagService.create(dtoSmallName));
        assertThrows(InvalidRequestException.class, () -> tagService.create(dtoLargeName));
    }

    @Test
    void getByExistingId() {
        long expected = 1;
        long actual;

        actual = assertDoesNotThrow(() -> tagService.read(expected)).getId();
        assertEquals(expected, actual);
    }
}