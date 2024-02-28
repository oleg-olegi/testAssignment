package com.example.testassignment.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class ParsingStringsFromCSV implements IParsingStringsFromCSV {

    /**
     * using apache commons lang3 methods to parse Nums from String
     */

    @Override
    public Integer parseInteger(String value) {
        return NumberUtils.createInteger(value);
    }

    @Override
    public Double parseDouble(String value) {
        return NumberUtils.createDouble(value);
    }
}
