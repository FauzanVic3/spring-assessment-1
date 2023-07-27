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
package my.com.vic3.spring.assessment1.service;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import my.com.vic3.spring.assessment1.model.Role;
import my.com.vic3.spring.assessment1.model.User;
import my.com.vic3.spring.assessment1.repository.RoleRepository;
import my.com.vic3.spring.assessment1.repository.UserRepository;
import my.com.vic3.spring.assessment1.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fauzan
 */
@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
        
    /**
     * Signup new user
     * 
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return Optional of User
     */
    public Optional<User> signup(String username, String password, String firstName, String lastName){

        // If user is not present then create new user
        if(!userRepository.findByUsername(username).isPresent()){
            
            Optional<Role> role = roleRepository.findByRoleName("CUSTOMER");
            
            return Optional.of(
                    userRepository
                            .save(
                                    new User(
                                            username, 
                                            passwordEncoder.encode(password),                                            
                                            firstName, 
                                            lastName,
                                            role.get()
                                    )
                            )
            );
        }
        
        return Optional.empty();
    }
    
    /**
     * Signin existing user
     * 
     * @param username
     * @param password
     * @return Optional of JWT token
     */
    public Optional<String> signin(String username, String password){
        log.info("New user attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            }catch(AuthenticationException e){
                log.info("Log in failed for user {}", username);
            }
        }
        return token;
    }
    
    public List<User> getAll(){
        return userRepository.findAll();
    }
    
    public Optional<User> getOneUser(String username){
        return userRepository.findByUsername(username);
    }
}
