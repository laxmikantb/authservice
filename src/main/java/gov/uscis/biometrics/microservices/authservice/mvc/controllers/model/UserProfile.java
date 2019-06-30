package gov.uscis.biometrics.microservices.authservice.mvc.controllers.model;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserProfile
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T10:12:41.308805-04:00[America/New_York]")
public class UserProfile   {
  @JsonProperty("username")
  private String username = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("phone")
  private String phone = null;

  @JsonProperty("roles")
  @Valid
  private List<String> roles = null;

  /**
   * Get username
   * @return username
  **/
  @ApiModelProperty(example = "johndoe", value = "")

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(example = "John", value = "")

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get lastName
   * @return lastName
  **/
  @ApiModelProperty(example = "Doe", value = "")

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(example = "johndoe@email.com", value = "")

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get phone
   * @return phone
  **/
  @ApiModelProperty(value = "")

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Get roles
   * @return roles
  **/
  @ApiModelProperty(value = "")

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

}
