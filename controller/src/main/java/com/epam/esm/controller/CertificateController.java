package com.epam.esm.controller;

import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.epam.esm.service.util.parser.UrlArrayParser.parse;

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
    public PagedModel<CertificateDto> read(@RequestParam(required = false) String tags,
                                           @RequestParam(required = false) String sorting,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) Integer page
    ) {
        SearchOptions options = SearchOptions.builder()
                .sorting(sorting)
                .subname(name)
                .subdescription(description)
                .pageNumber(page)
                .build();
        return certificateService.read(options, parse(tags));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto readById(@PathVariable Long id) {
        return certificateService.read(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto create(@RequestBody CertificateDto certificateDto) {
        return certificateService.create(certificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        certificateService.delete(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@RequestBody CertificateDto certificateDto, @PathVariable Long id) {
        CertificateDto identifiedCertificateDto = certificateDto.toBuilder().id(id).build();
        certificateService.update(identifiedCertificateDto);
    }
}
