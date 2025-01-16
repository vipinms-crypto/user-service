package com.user.userservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.user.userservice.dto.responsedto.LoginResponseDto;
import com.user.userservice.entity.User;
import com.user.userservice.repository.UserRepository;
import com.user.userservice.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService{

	public static final String SECRETKEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	UserRepository userRepo;
	public JWTServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	@Override
	public String generateJwtToken(String userName) {
		log.info("Enterd in to generateJwtToken()");
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().claims().add(claims).subject(userName).issuedAt(new Date(System.currentTimeMillis()
				)).expiration(new Date(System.currentTimeMillis() * 60 *6- 15)).and().signWith(getSecretKey()).compact();
		
	}
	private SecretKey getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRETKEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Extracts the username from the JWT token. Uses the extractClaim method to get
	 * the subject (username) from the token.
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
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
		return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
	}
	
	
	@Override
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
	 * Extracts the expiration date from the JWT token. Uses the extractClaim method
	 * to get the expiration date from the token.
	 * 
	 * @param token
	 * @return
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	@Override
	public void getUserDetails(LoginResponseDto resDto, String userName) {
		User user = userRepo.findByUserName(userName);
		resDto.setUserId(user.getUserId());
		resDto.setUserName(userName);
	}

}
