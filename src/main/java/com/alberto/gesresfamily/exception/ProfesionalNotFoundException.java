package com.alberto.gesresfamily.exception;

public class ProfesionalNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Profesional no encontrado";

    //hago dos metodos uno para poder pasar un mensaje y otro que no pasa un mensaje por defecto.
    public ProfesionalNotFoundException(String message) {
        super(message);
    }

    public ProfesionalNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
