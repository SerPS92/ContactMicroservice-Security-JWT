package com.example.ContactMicroserviceSecurityJWT.Controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

import static com.example.ContactMicroserviceSecurityJWT.Config.Constants.KEY;
import static com.example.ContactMicroserviceSecurityJWT.Config.Constants.LIFETIME;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping(value = "login", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login(@RequestParam(name = "user") String user,
                                        @RequestParam(name = "pwd") String pwd){
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user, pwd));
            return new ResponseEntity<>(getToken(auth), HttpStatus.OK);
        } catch (AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String getToken(Authentication auth){
        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + LIFETIME ))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
        return token;

    }
}
