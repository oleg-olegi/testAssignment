package com.example.testassignment.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.example.testassignment.model.Constants.MANDATORY_FIELDS;

@Component
public class CheckParamsForProcessRequest implements ICheckParamsForProcessRequest {
    // Проверка корректности параметра column
    @Override
    public void checkColumnParam(String column) {
        if (StringUtils.isBlank(column) && !MANDATORY_FIELDS.contains(column)) {
            throw new IllegalArgumentException("Invalid parameter for value column");
        }
    }
}
