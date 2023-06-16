package com.example.demo.config;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) throws UnsupportedEncodingException {
        String temp = Jwts.builder().setClaims(new HashMap<>()).setSubject(user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes("UTF-8")).compact();
        return temp;
    }

    public String parseJwt(String jwt) throws UnsupportedEncodingException {
        return Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(jwt).getBody().getSubject();
    }
}
