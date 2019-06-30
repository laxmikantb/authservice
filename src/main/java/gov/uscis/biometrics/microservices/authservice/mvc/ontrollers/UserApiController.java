package gov.uscis.biometrics.microservices.authservice.mvc.ontrollers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.uscis.biometrics.microservices.authservice.data.entity.User;
import gov.uscis.biometrics.microservices.authservice.data.repository.UserRepository;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.ResetPassword;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.Token;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UsernamePassword;
import gov.uscis.biometrics.microservices.authservice.security.jwt.JWTFilter;
import gov.uscis.biometrics.microservices.authservice.security.jwt.TokenProvider;
/**
 * 
 * @author Laxmikant Bopalkar
 */
@RestController
public class UserApiController {
	protected static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

   
	@GetMapping(value = "/user/profile", produces = "application/json")
	public ResponseEntity<UserProfile> getCurrentUser() {
		return ResponseEntity.ok(tokenProvider
				.parseToken((String) SecurityContextHolder.getContext().getAuthentication().getCredentials()));
	}

	@PostMapping(value = "/user/login", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Token> login(@Valid UsernamePassword body) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				body.getUsername(), body.getPassword());

		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		try {
			User user = userRepository.getOne(body.getUsername());
			if (user != null) {
				String jwt = tokenProvider.createToken(user);
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, JWTFilter.BEARER_HEADER + jwt);
				Token token = new Token();
				token.setToken(jwt);
				return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
			}
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/user/logout")
	public ResponseEntity<Void> logout() {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

    @PostMapping(value = "/user/resetPassword",consumes = "application/json")
 	public ResponseEntity<Void> resetPassword(@Valid ResetPassword body) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}
