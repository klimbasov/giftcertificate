package com.epam.esm.controller;

import com.epam.esm.hateoas.EntityLinkCreator;
import com.epam.esm.hateoas.PageLinkCreator;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.util.LinksSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final EntityLinkCreator<UserDto> entityLinkCreator;
    private final PageLinkCreator pageLinkCreator;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService,
                          EntityLinkCreator<UserDto> linkCreator,
                          PageLinkCreator pageLinkCreator) {
        this.userService = userService;
        this.entityLinkCreator = linkCreator;
        this.pageLinkCreator = pageLinkCreator;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserDto> read(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer page) {
        SearchOptions options = SearchOptions.builder()
                .subname(name)
                .pageNumber(page)
                .build();
        PagedModel<UserDto> model = userService.read(options);
        LinksSetter.setLinks(model, entityLinkCreator, pageLinkCreator);
        return model;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readById(@PathVariable Long id) {
        UserDto model = userService.read(id);
        entityLinkCreator.addLinks(model);
        return model;
    }
}
