package com.cslgduke.demo.core.test.jackson;

import lombok.*;

/**
 * @author i565244
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InnerDto {
    private Integer index;
    private String desc;
}