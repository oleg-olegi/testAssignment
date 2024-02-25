package com.example.testassignment.service;

import org.springframework.stereotype.Service;

@Service
public class CheckParamsForProcessRequest implements CheckParamsForProcessRequestInterface {
    @Override
    public void checkSortParam(String sort) {
        // Проверка корректности параметра sort
        if (!sort.equalsIgnoreCase("ASC") && !sort.equalsIgnoreCase("DESC")) {
            throw new IllegalArgumentException("Invalid value for parameter sort");
        }
    }

    @Override
    public void checkColumnParam(String column) {
        // Проверка корректности параметра column
        String[] validColumns = {"book", "author", "numPages", "publicationDate", "rating", "numberOfVoters"};
        boolean isValidColumn = false;
        for (String validColumn : validColumns) {
            if (column.equalsIgnoreCase(validColumn)) {
                isValidColumn = true;
                break;
            }
        }
        if (!isValidColumn) {
            throw new IllegalArgumentException("Invalid value for parameter column");
        }
    }
}
