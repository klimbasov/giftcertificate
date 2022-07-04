package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDto readById(@PathVariable Long id) {
        return tagService.read(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<TagDto> read(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String sorting,
                                   @RequestParam(required = false) Integer page) {
        SearchOptions options = SearchOptions.builder()
                .sorting(sorting)
                .subname(name)
                .pageNumber(page)
                .build();
        return tagService.read(options);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
