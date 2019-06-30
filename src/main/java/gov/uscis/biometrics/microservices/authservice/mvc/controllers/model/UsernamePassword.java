package gov.uscis.biometrics.microservices.authservice.mvc.controllers.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UsernamePassword
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T10:12:41.308805-04:00[America/New_York]")
public class UsernamePassword   {
  @JsonProperty("username")
  private String username = null;

  @JsonProperty("password")
  private String password = null;

  /**
   * The user name for login
   * @return username
  **/
  @ApiModelProperty(example = "johndoe", value = "The user name for login")

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * The password for login
   * @return password
  **/
  @ApiModelProperty(value = "The password for login")

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
