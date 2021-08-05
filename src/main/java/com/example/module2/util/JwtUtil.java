package com.example.module2.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//@Service
public class JwtUtil {

  private String SECRET_KEY;

//  public String extractUsername(String token) {
//      return exctractClaim(token, Claims::getSubject);
//  }
//
//    public <T> T exctractClaim(String token, Function<Claims, T> claimsResolver) {
//      final Claims claims = extractAllClaims(token);
//      return claimsResolver.apply(claims);
//    }
//
//    public Date extractExpiration(String token) {
//      return exctractClaim(token,Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//      return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(UserDetails userDetails) {
//      Map<String, Object> claims = new HashMap<>();
//      return createToken(claims, userDetails.getUsername());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//      return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//              .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
//              .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

  @Value("secret")
  private String secretKey;

  @Value("18000000")
  private Long expiration;

  @Value("Bearer")
  private String tokenType;

  public String extractUsername(String jwt) {
    Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwt)
            .getBody();

    return claims.getSubject();
  }

  public Date extractExpiration(String jwt) {
    return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwt)
            .getBody().getExpiration();
  }

  public boolean validateToken(String jwt) {
    try {
      Jwts.parser()
              .setSigningKey(secretKey)
              .parseClaimsJws(jwt);

      return true;
    } catch (SignatureException e) {
      LOGGER.error("Invalid JWT signature ", e);
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      LOGGER.error("Expired JWT token ", e);
    } catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT token ", e);
    } catch (UnsupportedJwtException e) {
      LOGGER.error("Unsupported JWT token ", e);
    } catch (IllegalArgumentException e) {
      LOGGER.error("JWT claims are missing ", e);
    }

    return false;
  }

  public String createToken(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    Date creationTime = new Date();
    Date expirationTime = new Date(creationTime.getTime() + expiration);

    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(creationTime)
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
  }
}
