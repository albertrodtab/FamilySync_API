package com.alberto.gesresfamily.exception;

public class BadRequestException extends Exception{
    private static final String DEFAULT_ERROR_MESSAGE = "Bad Request";

    public BadRequestException(){
        super(DEFAULT_ERROR_MESSAGE);
    }
}

