package com.example.demo.rest;

import com.example.demo.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RequestMapping(value = {"/account"})
@Validated
@RestController
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping("/deposit")
    public void deposit(@RequestParam("amount") BigDecimal amount) {
        accountService.depositOrder(amount);
    }

    @GetMapping(value = "/withdraw")
    public void withdraw(@RequestParam("amount") BigDecimal amount) {
        accountService.withdrawOrder(amount);
    }
}