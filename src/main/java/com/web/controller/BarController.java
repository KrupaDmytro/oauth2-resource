package com.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.web.dto.Bar;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Controller
public class BarController {

    public BarController() {
        super();
    }

    // API - read
    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/bars/{id}")
    @ResponseBody
    public Bar findById(@PathVariable final long id) {
        return new Bar(14, "test");
    }

    // API - write
    @PreAuthorize("#oauth2.hasScope('write')")
    @RequestMapping(method = RequestMethod.POST, value = "/bars" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Bar create(@RequestBody final Bar bar) {
        bar.setId(2);
        return bar;
    }



}
