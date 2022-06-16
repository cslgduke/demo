package com.example.demo.config;

import com.example.demo.service.list.Validator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author i565244
 */
@Service
@Data
public class ValidatorManager {
    @Autowired
    private List<Validator> validatorList;

    public Validator getValidaror(String validationType){
        return validatorList.stream().filter(s -> validationType.equals(s.validationType())).findFirst()
                .orElseThrow(() -> new RuntimeException("no validator for " + validationType ));
    }


}
