package com.example.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author i565244
 */
@RestController
@RequestMapping("/spring")
@Slf4j
public class SpringController {

    @Autowired
    private Environment env;

    @RequestMapping("/env")
    public Object env() {
        return env;
    }


}
