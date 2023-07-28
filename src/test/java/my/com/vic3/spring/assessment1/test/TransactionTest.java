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
package my.com.vic3.spring.assessment1.test;

import groovy.util.logging.Slf4j;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import my.com.vic3.spring.assessment1.helper.JSONUtil;
import my.com.vic3.spring.assessment1.security.JwtProvider;
import my.com.vic3.spring.assessment1.service.AuthenticationService;
import static org.hamcrest.Matchers.equalTo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Fauzan
 */
@Slf4j
@RunWith(SpringRunner.class)
public class TransactionTest {
        
    private Logger log = LoggerFactory.getLogger(TransactionTest.class);
    private final String authEndpoint = "http://localhost:8080/users/signin";
    private final String endpoint = "http://localhost:8080/api/v1/transaction";
    private int page = 1;
    private int size = 10;
    private String bearerToken;
    
    @Autowired
    public TransactionTest() {

    }
    
    @BeforeAll
    public void setup() {

    }
    
    public String getBearerToken(){
        String authBody =   "{\n" +
                    "  \"username\": \"Admin\",\n" +
                    "  \"password\": \"Admin\"\n" +
                    "}";
        
        String bearerToken = "";
        
        bearerToken =
                given()
                    .contentType(ContentType.JSON)
                    .body(authBody)
                .when()
                    .post(authEndpoint)                    
                .then()
                    .extract()
                    .response()
                    .asString();
                    
        log.info("Bearer token : "+bearerToken);
        
        return bearerToken;
    }

    @Test
    public void loginTest(){
        String authBody =   "{\n" +
                            "  \"username\": \"Admin\",\n" +
                            "  \"password\": \"Admin\"\n" +
                            "}";
        
        var response =
                given()
                    .contentType(ContentType.JSON)
                    .body(authBody)
                .when()
                    .post(authEndpoint)                    
                .then()
                    .assertThat()
                        .statusCode(200);
        
//        response.log().body();       
    }
        
    @Test
    public void getAllTransactionTest(){
        
        String bearerToken = getBearerToken();
        
        var response =
                given()
                    .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                    .queryParam("page", page)
                    .queryParam("size", size)
                .when()
                    .get(endpoint + "s")
                .then()
                    .assertThat()
                        .statusCode(200);
        
//        response.log().body();
    }
        
    
}
