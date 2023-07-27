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
package my.com.vic3.spring.assessment1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 
 * @author Fauzan
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

//    @Autowired
//    private AuthenticationUserDetailService authenticationUserDetailService;
////    @Autowired
//    private JwtTokenFilter jwtTokenFilter;
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/users/signin",
            "/users/signup"
    };
    
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            // configure AuthenticationManager so that it knows from where to load
            // user for matching credentials
            // Use BCryptPasswordEncoder
//            auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
            
        // We don't need CSRF for this example
        httpSecurity
                .csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http    
//                .authorizeRequests()
//                .antMatchers("/swagger-ui/**").permitAll()
//                .antMatchers("/v3/api-docs/**").permitAll()
//                .antMatchers("/docs/**").permitAll()
//                .antMatchers("/swagger-resources").permitAll()
//                .antMatchers("/swagger-resources/**").permitAll()
//                .antMatchers("/swagger-ui/index.html").permitAll()
//                .antMatchers("/configuration/**").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/public").permitAll()
//                .antMatchers("/users/signin").permitAll()
//                .antMatchers("/v3/api-docs").permitAll()
//                .antMatchers("/swagger-ui/**").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/swagger-ui/.html").permitAll()
//                .antMatchers("/swagger-ui/index.html").permitAll()
//                .antMatchers("/").permitAll()
//                .anyRequest()
////                .permitAll();
//                .authenticated();
//                
//        
//        
//        // Entry points
////        http.authorizeRequests()
////                .antMatchers("/**").permitAll()
////                .antMatchers("/users/signin").permitAll()
////                .antMatchers("/v3/api-docs").permitAll()
////                .antMatchers("/swagger-ui/**").permitAll()
////                // Disallow everything else..
////                .anyRequest().authenticated();
//
//        // Disable CSRF (cross site request forgery)
//        http.csrf().disable();
//
//        // No session will be created or used by spring security
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        jwtTokenFilter = new JwtTokenFilter(authenticationUserDetailService);
//        
//        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // Allow swagger to be accessed without authentication
//        web.ignoring().antMatchers("/v3/api-docs/**")//
//                .antMatchers("/swagger-resources/**")//
//                .antMatchers("/swagger-ui/index.html")//
//                .antMatchers("/configuration/**")//
//                .antMatchers("/webjars/**")//
//                .antMatchers("/public");
//        
//    }
    
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    




}