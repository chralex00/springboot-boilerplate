package com.zeniapp.segmentmiddleware.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    public static String generateJwt(String subject, Date expiration, Map<String, ?> claims, String secret) throws UnsupportedEncodingException {
        JwtBuilder jwtBuilder = Jwts
            .builder()
            .setSubject(subject)
            .setExpiration(expiration);

        claims.forEach((key, value) -> jwtBuilder.claim(key, value));
        
        return jwtBuilder.signWith(SignatureAlgorithm.HS256, secret.getBytes("UTF-8")).compact();
    }

    public static Map<String, Object> decodeJwt(String jwt, String secret, String[] claimNames) throws UnsupportedEncodingException, ExpiredJwtException {
        Jws<Claims> decodedJwt = Jwts
            .parser()
            .setSigningKey(secret.getBytes("UTF-8"))
            .parseClaimsJws(jwt);

        Date expiration = decodedJwt.getBody().getExpiration();

        if (new Date().after(expiration)){
            throw new ExpiredJwtException(decodedJwt.getHeader(), null, "Session expired");
        }

        String subject = decodedJwt.getBody().getSubject();

        Map<String, Object> claims = new HashMap<>();
        claims.put("subject", subject);
        claims.put("expiration", expiration);

        for (String claimName : claimNames) {
            Object claimValue = decodedJwt.getBody().get(claimName);
            claims.put(claimName, claimValue);
        }

        return claims;
    }
}
