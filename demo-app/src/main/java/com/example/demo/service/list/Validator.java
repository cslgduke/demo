package com.example.demo.service.list;

/**
 * @author i565244
 */
public interface Validator<T> {
    public boolean validate(T t);

    public String validationType();
}
