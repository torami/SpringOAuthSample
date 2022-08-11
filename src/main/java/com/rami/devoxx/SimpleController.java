package com.rami.devoxx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleController.class);
    @GetMapping("/")
    public String publicPage(){
        return "c'est une page Public";
    }

    @PreAuthorize("hasRole('robot')")
    @GetMapping("/private")
        public String privatePage (Authentication authentication){
        LOG.trace("Ceci est une trace Bonjour");
        LOG.info("Rami est Là");
        LOG.warn("Attention");
        LOG.error("Danger");
        return "Bonjour Monsieur $["
                +authentication.getName()
                + "]$";
    }
}
