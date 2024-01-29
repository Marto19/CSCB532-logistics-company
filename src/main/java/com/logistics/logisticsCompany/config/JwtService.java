package com.logistics.logisticsCompany.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
//JWT validation service
//THIS IS CLASS IS MEANT TO MANIPULATE THE JWT TOKEN - Json Web Token
//generating, validating, extracting, ect
@Service
public class JwtService {

    private static final String SECRET_KEY = "9cb7d8506abbddfcc041c82d026a1964b087cc460266b2292894992dbf856cee";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //method to extract a single claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    public  String generateToken(UserDetails userDetails){      //generating token from the user itself
        return  generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))//token valid for 24 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //methods to allow us to extract the claims from the token or a single one
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())  //sign in key is a key that digitally signs the JWT
                //it helps us verify that the sender of that jwt is who it claims to be
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //method to validate the token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //method to extract the expiration of the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //method to check if the token is expired
    

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
