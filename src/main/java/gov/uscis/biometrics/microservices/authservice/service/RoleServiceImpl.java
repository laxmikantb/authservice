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
import gov.uscis.biometrics.microservices.authservice.data.repository.AuthorityRepository;
import gov.uscis.biometrics.microservices.authservice.exception.AlreadyExistsException;
import gov.uscis.biometrics.microservices.authservice.exception.NotFoundException;

/**
 * Implementation of the {@link RoleService}.
 * 
 * @author Ryan Powell
 */
@Service
public class RoleServiceImpl implements RoleService {
	protected static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional(readOnly = true)
	public List<String> getAllRoles() {
		List<Authority> entities = authorityRepository.findAll();

		List<String> roles = new ArrayList<>();
		for (Authority entity : entities) {
			roles.add(entity.getName());
		}

		return roles;
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	public void createRole(String roleName) throws AlreadyExistsException {
		try {
			if (authorityRepository.getOne(roleName) != null) {
				throw new AlreadyExistsException();
			}
		} catch (EntityNotFoundException ex) {
		}
		Authority authority = new Authority();
		authority.setName(roleName);
		authorityRepository.save(authority);
	}

	@Override
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	public void deleteRole(String roleName) throws NotFoundException {
		try {
			if (authorityRepository.getOne(roleName) != null) {
				authorityRepository.deleteById(roleName);
			}
		} catch (EntityNotFoundException ex) {
		}

		throw new NotFoundException();
	}
}
