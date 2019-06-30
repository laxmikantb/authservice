package gov.uscis.biometrics.microservices.authservice.mvc.ontrollers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gov.uscis.biometrics.microservices.authservice.exception.AlreadyExistsException;
import gov.uscis.biometrics.microservices.authservice.exception.NotFoundException;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile;
import gov.uscis.biometrics.microservices.authservice.service.UserService;

/**
 * Implementation of the {@link UsersApi}.
 * 
 * @author Ryan Powell
 */
@RestController
public class UsersApiController {
	protected static final Logger logger = LoggerFactory.getLogger(UsersApiController.class);

	@Autowired
	private UserService userService;

    @GetMapping(value = "/users", produces = "application/json")
	public ResponseEntity<List<UserProfile>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

    @GetMapping(value = "/users/{username}",produces = "application/json")
	public ResponseEntity<UserProfile> getUser(String username) {
		return ResponseEntity.ok(userService.getUser(username));
	}

    @PostMapping(value = "/users",
            produces = "application/json", 
            consumes = "application/json")
	public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile userProfile) {
		UserProfile createdUserProfile;

		try {
			createdUserProfile = userService.create(userProfile);
		} catch (AlreadyExistsException aee) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserProfile);
	}

    @PutMapping(value = "/users/{username}",consumes = "application/json")
	public ResponseEntity<Void> updateUser(String username, @Valid UserProfile userProfile) {
		try {
			userService.update(username, userProfile);
		} catch (NotFoundException nfe) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

    @DeleteMapping(value = "/users/{username}")
	public ResponseEntity<Void> deleteUser(String username) {
		try {
			userService.delete(username);
		} catch (NotFoundException nfe) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
