package gov.uscis.biometrics.microservices.authservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.uscis.biometrics.microservices.authservice.data.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
	// no additional methods
}
