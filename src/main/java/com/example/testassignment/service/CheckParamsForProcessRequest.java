package com.example.testassignment.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.example.testassignment.model.Constants.MANDATORY_FIELDS;

@Service
public class CheckParamsForProcessRequest implements CheckParamsForProcessRequestInterface {

    /**
     * Проверка корректности параметра column
     * @param column - column for check
     */
    @Override
    public void checkColumnParam(String column) {
        if (StringUtils.isNoneBlank(column) && !MANDATORY_FIELDS.contains(column.toLowerCase())) {
            throw new IllegalArgumentException("Invalid value for parameter column");
        }
    }
}
