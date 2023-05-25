package com.bwgjoseph.springsecuritystackoverflowerror;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

    @GetMapping("/filter")
    public String filter() {
        return "filter";
    }
}

