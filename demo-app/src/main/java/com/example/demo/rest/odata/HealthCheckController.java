package com.example.demo.rest.odata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author i565244
 */

@RestController
@RequestMapping("/api/v1/health")
@Slf4j
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Object> healthCheck() {
        log.info("Health check called");
        return new ResponseEntity("Success,Service is Active", HttpStatus.OK);

    }
}
