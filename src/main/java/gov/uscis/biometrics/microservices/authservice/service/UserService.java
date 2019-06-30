package gov.uscis.biometrics.microservices.authservice.service;

import java.util.List;

import gov.uscis.biometrics.microservices.authservice.data.entity.User;
import gov.uscis.biometrics.microservices.authservice.exception.AlreadyExistsException;
import gov.uscis.biometrics.microservices.authservice.exception.NotFoundException;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile;

/**
 * Interface for performing business logic operations on a {@link UserProfile).
 * 
 * @author Ryan Powell
 */
public interface UserService {
	/**
	 * Gets all users from the data store.
	 * 
	 * @return a list of all {@link UserProfile}s in the data store.
	 */
	List<UserProfile> getAllUsers();

	/**
	 * Gets the {@link UserProfile} for the provided {@code username}.
	 * 
	 * @param username the user to get
	 * @return {@link UserProfile}
	 */
	UserProfile getUser(String username);

	/**
	 * Creates the {@link User} in the data store from the provided
	 * {@code userProfile}.
	 * 
	 * @param userProfile the {@link UserProfile} to create
	 * @return the created {@link UserProfile}
	 * @throws AlreadyExistsException when the {@code username} already exists
	 */
	UserProfile create(UserProfile userProfile) throws AlreadyExistsException;

	/**
	 * Update the {@link User} for the provided {@link username} in the data store.
	 * 
	 * @param username    the user to update
	 * @param userProfile the {@link UserProfile} to update
	 * @throws NotFoundException when the {@code username} does not exist
	 */
	void update(String username, UserProfile userProfile) throws NotFoundException;

	/**
	 * Delete the {@link User} for the provided {@link username} from the data
	 * store.
	 * 
	 * @param username the user to delete
	 * @throws NotFoundException when the {@code username} does not exist
	 */
	void delete(String username) throws NotFoundException;
}
