package com.alberto.gesresfamily.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    //Este Token se genera usando el KeyGeneratorExample
    private final static String ACCESS_TOKEN_SECRET = "sFPX3m5OcsQpRslyioH8RYQUQRK+c2YA8zf7mrDrEPY=";

    //Esta variable está configurada a 2_592_000L segundos, lo que equivale a 30 días
    //Por lo tanto, el token generado tendrá una validez de 30 días a partir del momento de su emisión.
    //Después de ese período, el token expirará y no será válido para su uso.
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public static String createToken (String nombre, String email) {
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);

        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();

        // Guardar el token en un archivo
        saveTokenToFile(token);

        return token;
    }

    public static UsernamePasswordAuthenticationToken getAuthentication (String token){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody ( ) ;

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken (email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }

    public static boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            return !expirationDate.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private static void saveTokenToFile(String token) {
        try {
            File file = new File("token.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readTokenFromFile() {
        try {
            return new String(Files.readAllBytes(Paths.get("token.txt")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
