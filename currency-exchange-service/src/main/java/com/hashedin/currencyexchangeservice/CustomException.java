package com.hashedin.currencyexchangeservice;

public class CustomException extends Exception{

    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}