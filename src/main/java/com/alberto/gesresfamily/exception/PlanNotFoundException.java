package com.alberto.gesresfamily.exception;

public class PlanNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Plan no encontrado";

    //hago dos metodos uno para poder pasar un mensaje y otro que no pasa un mensaje por defecto.
    public PlanNotFoundException(String message) {
        super(message);
    }

    public PlanNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
