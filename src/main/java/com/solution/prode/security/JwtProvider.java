package com.solution.prode.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${app.tokenSecretKey}")
    private String secretKey;

    @Value("${app.tokenExpiration}")
    private int tokenExpiration;

    public String generateToken(Authentication authentication) {

    	UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpiration);
        
        // To generate new valid secretkeys:
        //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        //String base64Key = Encoders.BASE64.encode(key.getEncoded());
        
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(Long.toString(userDetail.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex) {
            logger.error("Invalid token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported token");
        } catch (IllegalArgumentException ex) {
            logger.error("Token claims string is empty.");
        } catch (Exception ex) {
        	logger.error("Token error occurred.");
        }
        return false;
    }
}

