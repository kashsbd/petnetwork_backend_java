package com.martersolutions.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.martersolutions.model.OwnerCredential;
import com.martersolutions.repository.OwnerCredentialRepository;
import com.martersolutions.utils.Constants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private OwnerCredentialRepository ownerCreRepo;


	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rep, FilterChain chain)
			throws ServletException, IOException {
		String header = req.getHeader(Constants.HEADER_STRING);
		String email = null;
		String authToken = null;

		if (header != null && header.startsWith(Constants.TOKEN_PREFIX)) {
			authToken = header.replace(Constants.TOKEN_PREFIX, "");

			try {
				email = jwtTokenUtil.getEmailFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
				// rep.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Can't get email from
				// token.");
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
				// rep.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expire.");
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Email or Password not valid.");
				// rep.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed.");
			}

		} else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			List<OwnerCredential> storedUsers = ownerCreRepo.findByEmail(email);

			if (jwtTokenUtil.validateToken(authToken, storedUsers.get(0))) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						storedUsers.get(0), null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(req, rep);
	}

}
