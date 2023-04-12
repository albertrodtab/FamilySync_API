package com.alberto.gesresfamily.exception;

public class ResidenteNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Residente no encontrado";

    //hago dos metodos uno para poder pasar un mensaje y otro que no pasa un mensaje por defecto.
    public ResidenteNotFoundException(String message) {
        super(message);
    }

    public ResidenteNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
