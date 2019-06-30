package gov.uscis.biometrics.microservices.authservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("prod")
@Configuration
@PropertySource(value = "file:/usr/local/tomcat/conf/application-prod.properties")
public class ExternalPropertiesConfiguration {

}
