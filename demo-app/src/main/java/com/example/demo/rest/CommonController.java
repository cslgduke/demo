package com.example.demo.rest;

import cn.amorou.uid.UidGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author i565244
 */
@RestController
public class CommonController {

    @Resource
    private UidGenerator uidGenerator;

    @PostMapping("/uuid")
    public String generateUUid(){
        var uuid = uidGenerator.getUID();
        return String.valueOf(uuid);
    }

}
