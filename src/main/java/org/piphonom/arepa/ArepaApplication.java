package org.piphonom.arepa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by piphonom
 */
@SpringBootApplication
public class ArepaApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ArepaApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ArepaApplication.class, args);
    }

}
