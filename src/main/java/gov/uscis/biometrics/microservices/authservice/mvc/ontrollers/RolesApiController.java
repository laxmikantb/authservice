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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.uscis.biometrics.microservices.authservice.exception.AlreadyExistsException;
import gov.uscis.biometrics.microservices.authservice.exception.NotFoundException;
import gov.uscis.biometrics.microservices.authservice.service.RoleService;

/**
 * 
 * @author Laxmikant Bopalkar
 */
@RestController
public class RolesApiController {
	protected static final Logger logger = LoggerFactory.getLogger(RolesApiController.class);

	@Autowired
	private RoleService roleService;

	@PostMapping("/roles")
	public ResponseEntity<Void> createRole(@Valid @RequestBody String body) {
		try {
			roleService.createRole(body);
		} catch (AlreadyExistsException aee) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

    @DeleteMapping("/roles/{name}")
	public ResponseEntity<Void> deleteRole(@RequestParam(name = "name", required = false) String name) {
		try {
			roleService.deleteRole(name);
		} catch (NotFoundException nfe) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

    @GetMapping("/roles")
	public ResponseEntity<List<String>> getAllRoles() {
		return ResponseEntity.ok(roleService.getAllRoles());
	}
}
