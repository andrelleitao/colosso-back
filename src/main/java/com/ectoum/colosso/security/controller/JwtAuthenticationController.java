package com.ectoum.colosso.security.controller;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.security.dto.JwtRequest;
import com.ectoum.colosso.security.dto.JwtResponse;
import com.ectoum.colosso.security.util.JwtTokenUtil;
import com.google.common.hash.Hashing;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws CustomMessageException {
		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();		
		
		// Username and password cannot be null.
		Objects.requireNonNull(email);
		Objects.requireNonNull(password);

		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		
		authenticate(email, sha256hex);

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws CustomMessageException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new CustomMessageException(messageSource.getMessage("administration.userDisabled", null, LocaleContextHolder.getLocale()));
		} catch (BadCredentialsException e) {
			throw new CustomMessageException(messageSource.getMessage("authentication.badCredentials", null, LocaleContextHolder.getLocale()));
		}
	}
}
