package com.cslgduke.demo.core.test.jdk.function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author i565244
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldInfoDTO {

    private String columnName;
    private String fieldName;
    private Boolean nullable;
    private Boolean primaryKey;
    private Boolean childColumn;
    private Boolean parentColumn;
    private Boolean longIdColumn;
    private Boolean buildPathColumn;
    private Boolean searchable;
    private Boolean fuzzSearchable;
    private Integer length;
    private Boolean unique;
    private Boolean filterable;
    private String valueHelpGroup;
}
