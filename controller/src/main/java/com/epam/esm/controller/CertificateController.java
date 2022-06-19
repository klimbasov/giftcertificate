package com.epam.esm.controller;

import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(final CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<CertificateDto> read(@RequestParam(required = false) String sorting,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) Long page
    ) {
        SearchOptions options = SearchOptions.builder()
                .sorting(sorting)
                .subname(name)
                .subdescription(description)
                .pageNumber(page)
                .build();
        return certificateService.get(options);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto readById(@PathVariable Integer id) {
        return certificateService.get(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto create(@RequestBody CertificateDto certificateDto) {
        return certificateService.add(certificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        certificateService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@RequestBody CertificateDto certificateDto, @PathVariable Integer id) {
        CertificateDto identifiedCertificateDto = certificateDto.toBuilder().id(id).build();
        certificateService.put(identifiedCertificateDto);
    }
}
