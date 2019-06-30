package gov.uscis.biometrics.microservices.authservice.mvc.controllers.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ResetPassword
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T10:12:41.308805-04:00[America/New_York]")
public class ResetPassword   {
  @JsonProperty("currentPassword")
  private String currentPassword = null;

  @JsonProperty("newPassword")
  private String newPassword = null;

  /**
   * The user's current password, not required if they do not have one.
   * @return currentPassword
  **/
  @ApiModelProperty(value = "The user's current password, not required if they do not have one.")

  public String getCurrentPassword() {
    return currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  /**
   * The user's new password.
   * @return newPassword
  **/
  @ApiModelProperty(value = "The user's new password.")

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

}
