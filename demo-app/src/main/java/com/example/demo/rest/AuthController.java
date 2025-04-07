package com.example.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author i565244
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @GetMapping("/openid/exchange")
    public String exchangeToken(HttpServletRequest request, @RequestParam(required = false) String code) {

        return "code:" + code;
    }


    @GetMapping("/openid/")
    public String token() {
        return "success";
    }

}
