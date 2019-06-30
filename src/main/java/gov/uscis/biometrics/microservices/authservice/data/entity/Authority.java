package gov.uscis.biometrics.microservices.authservice.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * An authority (a security role) used by Spring Security.
 * 
 * @author Ryan Powell
 */
@Entity
public class Authority implements Serializable {
	private static final long serialVersionUID = -7435184303687343023L;

	@Size(max = 50)
	@Id
	@Column(nullable = false, length = 50)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
