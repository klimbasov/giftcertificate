package com.epam.esm.controller;

import com.epam.esm.hateoas.linkers.entity.EntityLinkCreator;
import com.epam.esm.hateoas.linkers.page.PageLinkCreator;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.util.LinksSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.epam.esm.service.util.parser.UrlArrayParser.parse;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final EntityLinkCreator<CertificateDto> entityLinkCreator;
    private final PageLinkCreator pageLinkCreator;
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(final CertificateService certificateService,
                                 EntityLinkCreator<CertificateDto> linkCreator,
                                 PageLinkCreator pageLinkCreator) {
        this.certificateService = certificateService;
        this.entityLinkCreator = linkCreator;
        this.pageLinkCreator = pageLinkCreator;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<CertificateDto> read(@RequestParam(required = false) String tags,
                                           @RequestParam(required = false, defaultValue = "") String sorting,
                                           @RequestParam(required = false, defaultValue = "") String name,
                                           @RequestParam(required = false, defaultValue = "") String description,
                                           @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        SearchOptions options = SearchOptions.builder()
                .sorting(sorting)
                .subname(name)
                .subdescription(description)
                .pageNumber(page)
                .build();
        PagedModel<CertificateDto> model = certificateService.read(options, parse(tags));
        LinksSetter.setLinks(model, entityLinkCreator, pageLinkCreator);
        return model;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto readById(@PathVariable Long id) {
        CertificateDto model = certificateService.read(id);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto create(@RequestBody CertificateDto dto) {
        CertificateDto model = certificateService.create(dto);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto patch(@RequestBody CertificateDto dto, @PathVariable Long id) {
        dto.setId(id);
        certificateService.update(dto);
        CertificateDto model = certificateService.read(id);
        entityLinkCreator.addLinks(model);
        return model;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        certificateService.delete(id);
    }
}
