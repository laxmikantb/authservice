package gov.uscis.biometrics.microservices.authservice.service;

import java.util.List;

import gov.uscis.biometrics.microservices.authservice.exception.AlreadyExistsException;
import gov.uscis.biometrics.microservices.authservice.exception.NotFoundException;

public interface RoleService {
	List<String> getAllRoles();

	void createRole(String roleName) throws AlreadyExistsException;

	void deleteRole(String roleName) throws NotFoundException;
}
