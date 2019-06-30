package gov.uscis.biometrics.microservices.authservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.uscis.biometrics.microservices.authservice.data.entity.Authority;
import gov.uscis.biometrics.microservices.authservice.data.entity.User;
import gov.uscis.biometrics.microservices.authservice.data.repository.UserRepository;
import gov.uscis.biometrics.microservices.authservice.exception.AlreadyExistsException;
import gov.uscis.biometrics.microservices.authservice.exception.NotFoundException;
import gov.uscis.biometrics.microservices.authservice.mvc.controllers.model.UserProfile;

/**
 * Implementation of the {@link UserService).
 * 
 * @author Ryan Powell
 */
@Service
public class UserServiceImpl implements UserService {
	protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional(readOnly = true)
	public List<UserProfile> getAllUsers() {
		return toModelList(userRepository.findAll());
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional(readOnly = true)
	public UserProfile getUser(String username) {
		return toModel(userRepository.getOne(username));
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	public UserProfile create(UserProfile userProfile) throws AlreadyExistsException {
		try {
		    userRepository.getOne(userProfile.getUsername());
			throw new AlreadyExistsException();
		} catch (EntityNotFoundException  ex) {}

		return toModel(userRepository.save(toEntity(userProfile)));
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	public void update(String username, UserProfile userProfile) throws NotFoundException {
		try {
		User currentUser = userRepository.getOne(username);
		currentUser.setEmail(userProfile.getEmail());
		currentUser.setFirstName(userProfile.getFirstName());
		currentUser.setLastName(userProfile.getLastName());
		currentUser.setPhone(userProfile.getPhone());
		for (String role : userProfile.getRoles()) {
			Authority authority = new Authority();
			authority.setName(role);
			currentUser.getAuthorities().add(authority);
		}
		userRepository.save(currentUser);
		}
		catch (EntityNotFoundException ex) {}
		throw new NotFoundException();
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	public void delete(String username) throws NotFoundException {
		try {
			userRepository.getOne(username);
			userRepository.deleteById(username);
		}
		catch (EntityNotFoundException ex) {}
		throw new NotFoundException();
	}

	/**
	 * Converts a list of entity objects to a list of model objects.
	 * 
	 * @param userEntities the entities to convert
	 * @return a {@code List} of {@link UserProfile} models.
	 */
	List<UserProfile> toModelList(List<User> userEntities) {
		List<UserProfile> userModels = new ArrayList<>();
		for (User userEntity : userEntities) {
			userModels.add(toModel(userEntity));
		}
		return userModels;
	}

	/**
	 * Converts a single model to an entity.
	 * 
	 * @param userProfile the model to convert
	 * @return a {@link User} containing the same values as the provided
	 *         {@link UserProfile}.
	 */
	User toEntity(UserProfile userProfile) {
		User userEntity = new User();
		userEntity.setUserId(userProfile.getUsername());
		userEntity.setEmail(userProfile.getEmail());
		userEntity.setFirstName(userProfile.getFirstName());
		userEntity.setLastName(userProfile.getLastName());
		userEntity.setPhone(userProfile.getPhone());
		if (userProfile.getRoles() != null) {
			for (String role : userProfile.getRoles()) {
				Authority authority = new Authority();
				authority.setName(role);
				userEntity.getAuthorities().add(authority);
			}
		}
		return userEntity;
	}

	/**
	 * Convert an optional entity to a model.
	 * 
	 * @param optionalUserEntity the optional entity
	 * @return the {@link UserProfile} model
	 */
	UserProfile toModel(User userEntity) {
		if (userEntity != null) {
			UserProfile userModel = new UserProfile();
			userModel.setUsername(userEntity.getUserId());
			userModel.setFirstName(userEntity.getFirstName());
			userModel.setLastName(userEntity.getLastName());
			userModel.setEmail(userEntity.getEmail());
			userModel.setPhone(userEntity.getPhone());
			userModel.setRoles(new ArrayList<>());
			for (Authority authority : userEntity.getAuthorities()) {
				userModel.getRoles().add(authority.getName());
			}
			return userModel;
		}
		return null;
	}
}
