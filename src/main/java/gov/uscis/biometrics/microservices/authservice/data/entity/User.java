package gov.uscis.biometrics.microservices.authservice.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Persistent POJO describing a User.
 * 
 * @author Ryan Powell
 */
@Entity
@Table(name = "USER_DETAIL")
public class User implements Serializable {
	private static final long serialVersionUID = 5006492477325860556L;

	@Id
	@Column(length = 64)
	private String userId;

	@Column(nullable = true, length = 64)
	private String firstName;

	@Column(nullable = true, length = 64)
	private String lastName;

	@Column(nullable = true, length = 256)
	private String email;

	@Column(nullable = true, length = 64)
	private String phone;

	@Column(nullable = true, length = 256)
	private String password;

	@ManyToMany
	@JoinTable(name = "user_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "userId") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })
	private Set<Authority> authorities = new HashSet<>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
