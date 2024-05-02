package com.productapp.productapp.service;

import com.productapp.productapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class jwtService {
    private final String SECRET_KEY = "408703f894e7a2b33b90761b5908575f993efa5e2116f6e30ec5daa3693d4562";


    public boolean isValid(String token, UserDetails user){
        String username =extractUsername(token);
        return (username.equals(user.getUsername())) && isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public  String extractUsername(String token){
        return  extractClaims(token, Claims::getSubject);
    }

    public<T> T extractClaims(String token, Function<Claims, T> resolver){

        Claims claims= extractAllClaims(token);
        return  resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public String generateToken(User user){
        String token =
                Jwts.builder().subject(user.getUsername())
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                        .signWith(getSignKey())
                        .compact();
        return  token;
    }

    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
