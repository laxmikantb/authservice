package gov.uscis.biometrics.microservices.authservice.service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.uscis.biometrics.microservices.authservice.data.entity.User;
import gov.uscis.biometrics.microservices.authservice.data.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		try {
		User userByLoginFromDatabase = userRepository.getOne(lowercaseLogin);
		if ( userByLoginFromDatabase != null) 
			createSpringSecurityUser(lowercaseLogin, userByLoginFromDatabase);
		}
		catch (EntityNotFoundException ex) {
			
		}
		throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " + "database");
	}

	org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(lowercaseLogin, user.getPassword(),
				grantedAuthorities);
	}
}
