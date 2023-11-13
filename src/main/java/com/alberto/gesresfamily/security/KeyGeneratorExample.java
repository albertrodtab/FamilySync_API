package com.alberto.gesresfamily.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGeneratorExample {

    public static void main(String[] args) {
        // Generar una clave segura utilizando KeyGenerator
        SecretKey secretKey = generateSecureKey();

        // Convertir la clave a una cadena Base64 para usar como ACCESS_TOKEN_SECRET
        String accessTokenSecret = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("ACCESS_TOKEN_SECRET: " + accessTokenSecret);
    }

    private static SecretKey generateSecureKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave segura", e);
        }
    }
}
