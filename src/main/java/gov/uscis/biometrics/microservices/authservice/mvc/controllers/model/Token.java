package gov.uscis.biometrics.microservices.authservice.mvc.controllers.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Token
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T10:12:41.308805-04:00[America/New_York]")
public class Token   {
  @JsonProperty("token")
  private String token = null;

  /**
   * Get token
   * @return token
  **/
  @ApiModelProperty(example = "header.body.signature", value = "")

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
