package com.martersolutions.config;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.martersolutions.model.OwnerCredential;
import com.martersolutions.utils.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public String getEmailFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(OwnerCredential usr) {
		return doGenerateToken(usr.getEmail(), "ACCESS-TOKEN");
	}

	public String generateRefreshToken(OwnerCredential usr) {
		return doGenerateToken(usr.getEmail(), "REFRESH-TOKEN");
	}

	private String doGenerateToken(String subject, String type) {

		Claims claims = Jwts.claims().setSubject(subject);

		if (type.equals("ACCESS-TOKEN")) {
			claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

			LocalDateTime currentTime = LocalDateTime.now();

			return Jwts.builder().setClaims(claims).setIssuer("http://martersolutions.com")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(Date.from(currentTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
					.signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY).compact();
		} else {
			claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_REFRESH_TOKEN")));

			LocalDateTime currentTime = LocalDateTime.now();

			String token = Jwts.builder().setClaims(claims).setIssuer("http://martersolutions.com")
					.setId(UUID.randomUUID().toString()).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(Date.from(currentTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant()))
					.signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY).compact();

			return token;
		}

	}

	public Boolean validateToken(String token, OwnerCredential usrCre) {
		final String email = getEmailFromToken(token);
		return (email.equals(usrCre.getEmail()) && !isTokenExpired(token));
	}

}
