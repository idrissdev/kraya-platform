package com.kraya.platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration for API documentation.
 * Accessible at /swagger-ui.html
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI krayaOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        Server productionServer = new Server();
        productionServer.setUrl("https://api.kraya.com");
        productionServer.setDescription("Production Server");

        Contact contact = new Contact();
        contact.setName("Kraya Platform Support");
        contact.setEmail("slimani@slimatech.com");
        contact.setUrl("https://www.kraya.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Kraya Platform API")
                .version("1.0.0")
                .contact(contact)
                .description("Kraya is a SaaS platform that simplifies the debt resolution process by connecting debtors with flexible repayment options. " +
                        "These options include charitable donations, volunteering, and structured payment plans. " +
                        "Kraya fosters a collaborative network of institutions and organizations to create pathways for debt forgiveness and resolution.")
                .termsOfService("https://www.kraya.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}
