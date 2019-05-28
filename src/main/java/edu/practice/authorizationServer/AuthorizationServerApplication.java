package edu.practice.authorizationServer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthorizationServerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
