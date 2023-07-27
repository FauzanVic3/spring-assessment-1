/*
 * Copyright (C) 2023 Fauzan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package my.com.vic3.spring.assessment1;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import my.com.vic3.spring.assessment1.batch.BatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Fauzan
 */
@Slf4j
@SpringBootApplication
public class SpringAssessment1 implements CommandLineRunner{

    @Autowired
    private SpringAssessment1Configuration configuration;
    @Autowired
    private BatchConfiguration batchConfiguration;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringAssessment1.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting REST Service");
        log.info("Endpoints are available at http://localhost:8080/swagger-ui/index.html");    
    }
    
    @Bean
    public OpenAPI openAPI(
            @Value("${openapi.service.title}") String serviceTitle, 
            @Value("${openapi.service.version}") String serviceVersion,
            @Value("${openapi.service.description}") String serviceDescription
    ) {
        
        final String securitySchemeName = "bearerAuth";
        
        // swagger for exposing available endpoints
        return new OpenAPI()
            .components(
                    new Components()
                    .addSecuritySchemes(
                            securitySchemeName, 
                            new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .security(List.of(new SecurityRequirement().addList(securitySchemeName)))
            .info(
                    new Info()
                    .title(serviceTitle)
                    .description(serviceDescription)
                    .version(serviceVersion)
                        
            );
    }
}
