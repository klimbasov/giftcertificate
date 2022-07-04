package com.epam.esm.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserDto> read(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer page) {
        SearchOptions options = SearchOptions.builder()
                .subname(name)
                .pageNumber(page)
                .build();
        return userService.read(options);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readById(@PathVariable Long id) {
        return userService.read(id);
    }
}
