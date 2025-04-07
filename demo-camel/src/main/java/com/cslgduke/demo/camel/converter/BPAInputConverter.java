package com.cslgduke.demo.camel.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.apache.camel.TypeConverters;
import org.springframework.stereotype.Component;


/**
 * @author i565244
 */
@Component
@Converter(generateLoader = true)
@Slf4j
public class BPAInputConverter implements TypeConverters {

    @Converter
    public String toBPAInput(byte[] data, Exchange exchange) {
        TypeConverter converter = exchange.getContext().getTypeConverter();
        String s = converter.convertTo(String.class, data);
        return s;
    }

}
