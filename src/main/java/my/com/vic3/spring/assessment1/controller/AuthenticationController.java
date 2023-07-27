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
package my.com.vic3.spring.assessment1.controller;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import my.com.vic3.common.lib.handler.exception.BadRequestException;
import my.com.vic3.common.lib.handler.exception.NotFoundException;
import my.com.vic3.common.lib.handler.exception.UnauthorizedException;
import my.com.vic3.spring.assessment1.dto.LoginDto;
import my.com.vic3.spring.assessment1.dto.RegistrationDto;
import my.com.vic3.spring.assessment1.model.User;
import my.com.vic3.spring.assessment1.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fauzan
 */
@RestController
@RequestMapping("/users")
public class AuthenticationController {
    
    @Autowired
    AuthenticationService authenticationService;
    
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@RequestBody @Valid RegistrationDto registrationDto){
        return authenticationService
                .signup(registrationDto.getUsername(), 
                        registrationDto.getPassword(), 
                        registrationDto.getFirstName(),
                        registrationDto.getLastName())
                .orElseThrow(
                        () -> new BadRequestException("User already exists"));
    }
    
    @PostMapping("/signin")
    public String login(@RequestBody @Valid LoginDto loginDto) {
       return authenticationService
               .signin(loginDto.getUsername(), loginDto.getPassword())
               .orElseThrow(
                       ()-> new UnauthorizedException("Login Failed!\nUser unauthorised"));
    }
    
    @GetMapping
    public List<User> getAllUsers(){
        return authenticationService.getAll();
    }
    
    @GetMapping("/one")
    public User getOneUser(@QueryParam("username") String username){
        return authenticationService
                .getOneUser(username)
                .orElseThrow(
                        () -> new NotFoundException("Username does not exist"));
    }
}
