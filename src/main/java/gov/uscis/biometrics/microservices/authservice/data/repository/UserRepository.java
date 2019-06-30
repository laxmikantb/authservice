package gov.uscis.biometrics.microservices.authservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.uscis.biometrics.microservices.authservice.data.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	// no additional methods
}
