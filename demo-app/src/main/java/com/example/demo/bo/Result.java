package com.example.demo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Result<T> {
    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    public Result() {
    }

    public static Result<?> Ok(Integer code, String message, Object data) {
        return build(code, true, message, data);
    }

    public static Result<?> failed(Integer code, String message) {
        return build(code, false, message,null);
    }

    private static Result <?> build(Integer code, Boolean isSuccess, String message, Object data){
        return Result.builder()
                .code(code)
                .success(isSuccess)
                .message(message)
                .data(data)
                .build();
    }
}