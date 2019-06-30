package gov.uscis.biometrics.microservices.authservice.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import gov.uscis.biometrics.microservices.authservice.data.entity.Authority;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Create and validate JSON Web Tokens (JWT) to represent our {@link User}
 * entities.
 * 
 * @author Ryan Powell
 */
@Component
public class TokenProvider {
	private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";

	@Value("${microservice.authentication.jwt.secret}")
	private String secretKey;

	@Value("${microservice.authentication.jwt.token-validity-in-milliseconds}")
	private long tokenValidityInMilliseconds;

	/**
	 * Default constructor
	 */
	public TokenProvider() {
		// empty constructor
	}

	/**
	 * Parameterized constructor for unit testing
	 * 
	 * @param string
	 * @param l
	 */
	public TokenProvider(String secretKey, long tokenValidityInMilliseconds) {
		this.secretKey = secretKey;
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
	}

	public String createToken(gov.uscis.biometrics.microservices.authservice.data.entity.User user) {
		String authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		//@formatter:on
		return Jwts.builder().setSubject(user.getUserId()).claim("firstName", user.getFirstName())
				.claim("lastName", user.getLastName()).claim("email", user.getEmail()).claim("phone", user.getPhone())
				.claim(AUTHORITIES_KEY, authorities).signWith(SignatureAlgorithm.HS512, secretKey)
				.setExpiration(validity).compact();
		//@formatter:off
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.info("Invalid JWT signature.");
			logger.trace("Invalid JWT signature trace: {}", e);
		} catch (MalformedJwtException e) {
			logger.info("Invalid JWT token.");
			logger.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			logger.info("Expired JWT token.");
			logger.trace("Expired JWT token trace: {}", e);
		}
		return false;
	}

	/**
	 * Create the UserProfile based on the JSON Web Token (JWT) claims.
	 * 
	 * @param claims
	 *            the JWT claims
	 * @return the {@link tech.sapientgov.ms.mvc.model.UserProfile}
	 */
	public UserProfile parseToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

		gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile user = new gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile();
		user.setUsername(claims.getSubject());
		user.setFirstName(claims.get("firstName", String.class));
		user.setLastName(claims.get("lastName", String.class));
		user.setPhone(claims.get("phone", String.class));
		user.setEmail(claims.get("email", String.class));

		// get roles
		List<String> roles = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.collect(Collectors.toList());
		user.setRoles(roles);

		return user;
	}
}
