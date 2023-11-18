package com.alberto.gesresfamily.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            // El token no está presente o no está en el formato correcto, continúa con la cadena de filtros
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearerToken.replace("Bearer ", "");

        if (TokenUtils.isTokenValid(token)) {
            // Si el token es válido, establece la autenticación en el contexto de seguridad
            UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        } else {
            // Si el token no es válido, podrías realizar alguna lógica, como enviar una respuesta de error
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no válido");
            return;
        }

        filterChain.doFilter(request, response);
    }

    //con esto yo en postman tengo que introducir el token a mano en campo authorization Bearer Token

        /*if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);

        }
        filterChain.doFilter(request,response);
    }*/
}
