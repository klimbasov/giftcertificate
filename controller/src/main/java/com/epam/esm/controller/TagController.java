package com.epam.esm.controller;

import com.epam.esm.hateoas.linkers.entity.EntityLinkCreator;
import com.epam.esm.hateoas.linkers.page.PageLinkCreator;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.util.LinksSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final EntityLinkCreator<TagDto> entityLinkCreator;
    private final PageLinkCreator pageLinkCreator;
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService,
                         EntityLinkCreator<TagDto> entityLinkCreator,
                         PageLinkCreator pageLinkCreator) {
        this.tagService = tagService;
        this.entityLinkCreator = entityLinkCreator;
        this.pageLinkCreator = pageLinkCreator;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDto readById(@PathVariable Long id) {
        TagDto model = tagService.read(id);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @GetMapping(value = "/recommended")
    @ResponseStatus(HttpStatus.OK)
    public TagDto readMostUsedTagOfUserWithHighestOrderCost() {
        TagDto model = tagService.readMostUsedTagOfUserWithHighestOrderCost();
        entityLinkCreator.addLinks(model);
        return model;
    }

    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<TagDto> read(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String sorting,
                                   @RequestParam(required = false, defaultValue = "1") int page) {
        SearchOptions options = SearchOptions.builder()
                .sorting(sorting)
                .subname(name)
                .pageNumber(page)
                .build();
        PagedModel<TagDto> model = tagService.read(options);
        LinksSetter.setLinks(model, entityLinkCreator, pageLinkCreator);
        return model;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto dto) {
        TagDto model = tagService.create(dto);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
