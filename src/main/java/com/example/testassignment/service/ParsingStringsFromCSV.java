package com.example.testassignment.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class ParsingStringsFromCSV implements ParsingStringsFromCSVInterface {


    @Override
    public Integer parseInteger(String value) {
        return NumberUtils.createInteger(value);
    }

    @Override
    public Double parseDouble(String value) {
        return NumberUtils.createDouble(value);
    }
}
