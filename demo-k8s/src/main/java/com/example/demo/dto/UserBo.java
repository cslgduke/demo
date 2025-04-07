package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author i565244
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBo {
    private String id;
    private String no;
    private int age;
}
