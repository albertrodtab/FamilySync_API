package com.alberto.gesresfamily.exception;

public class FamiliarNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Familiar no encontrado";

    //hago dos metodos uno para poder pasar un mensaje y otro que no pasa un mensaje por defecto.
    public FamiliarNotFoundException(String message) {
        super(message);
    }

    public FamiliarNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
