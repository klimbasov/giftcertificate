package com.epam.esm.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @GetMapping("/")
    public String read(@RequestParam(required = false) String sorting,
                       @RequestParam(required = false) String tag
    ) {
        return "items" ;
    }

    @GetMapping("/{id}")
    public String readById(@PathVariable Integer id) {
        return "item " + id;
    }

    @PostMapping("/")
    public String create(){
        return "post items";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        return "deleted " + id;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id){
        return "put " + id;
    }
}
