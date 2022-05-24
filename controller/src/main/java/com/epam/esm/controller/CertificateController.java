package com.epam.esm.controller;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(final CertificateService certificateService){
        this.certificateService = certificateService;
    }

    @GetMapping("/")
    public String read(@RequestParam(required = false) String sorting,
                       @RequestParam(required = false) String tag,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String description
                      ) {
        return "items" ;
    }

    @GetMapping("/{id}")
    public CertificateDto readById(@PathVariable Integer id) {
        CertificateDto certificateDto = null;
        Optional<CertificateDto> certificateDtoOptional = certificateService.get(id);
        if(certificateDtoOptional.isPresent()){
            certificateDto = certificateDtoOptional.get();
        }else {

        }
        return certificateDto;
    }

    @PostMapping("/")
    public String create(@RequestBody CertificateDto certificateDto){
        return "post items";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        return "deleted " + id;
    }

    @PutMapping("/{id}")
    public String put(@RequestBody Map<String, String> params, @PathVariable Integer id){
        return "put " + id;
    }
}
