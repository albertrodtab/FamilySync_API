package com.alberto.gesresfamily.exception;

public class CentroNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Centro no encontrado";

    //hago dos metodos uno para poder pasar un mensaje y otro que no pasa un mensaje por defecto.
    public CentroNotFoundException(String message) {
        super(message);
    }

    public CentroNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
