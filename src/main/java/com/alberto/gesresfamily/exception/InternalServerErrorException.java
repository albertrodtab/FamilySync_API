package com.alberto.gesresfamily.exception;

public class InternalServerErrorException extends Exception{
    private static final String DEFAULT_ERROR_MESSAGE = "Internal Server Error";

    public InternalServerErrorException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
