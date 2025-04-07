package com.cslgduke.demo.core.test.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author i565244
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class OutterDto {

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private InnerDto inner;

    private List<InnerDto> inners;
}
