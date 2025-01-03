package com.user.userservice.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private String SECRET_KEY = "secret";

	/**
	 * Extracts the username from the JWT token. Uses the extractClaim method to get
	 * the subject (username) from the token.
	 * 
	 * @param token
	 * @return
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extracts the expiration date from the JWT token. Uses the extractClaim method
	 * to get the expiration date from the token.
	 * 
	 * @param token
	 * @return
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extracts a specific claim from the JWT token. Uses the provided
	 * claimsResolver function to extract the desired claim from the token's claims.
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the JWT token. Parses the token using the secret key
	 * to retrieve the claims.
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Checks if the JWT token has expired. Compares the token's expiration date
	 * with the current date.
	 * 
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Generates a new JWT token for the given username. Creates a token with the
	 * username as the subject and sets the expiration time.
	 * 
	 * @param username
	 * @return
	 */
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	/**
	 * Creates a JWT token with the provided claims and subject. Sets the issued
	 * date, expiration date, and signs the token with the secret key.
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	/**
	 * Validates the JWT token. Checks if the token's username matches the provided
	 * UserDetails username and if the token has not expired.
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}