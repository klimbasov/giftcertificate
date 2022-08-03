package com.epam.esm.auth.config;

import com.epam.esm.auth.filter.AuthenticationFilter;
import com.epam.esm.auth.validator.AudienceValidator;
import com.epam.esm.service.constant.permissions.Permission;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
    private final UserDetailsService service;

    @Autowired
    public Config(UserDetailsService service){
        this.service = service;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity security) throws Exception{
        security.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.GET, "/certificate/**", "/tag/**").hasAuthority(Permission.MARKET_READ.getAuthority())
                .antMatchers(HttpMethod.POST, "/certificate/**", "/tag/**").hasAuthority(Permission.MARKET_WRITE.getAuthority())
                .antMatchers(HttpMethod.PATCH, "/certificate/**", "/tag/**").hasAuthority(Permission.MARKET_WRITE.getAuthority())
                .antMatchers(HttpMethod.DELETE, "/certificate/**", "/tag/**").hasAuthority(Permission.MARKET_WRITE.getAuthority())
                .antMatchers(HttpMethod.GET, "/users/**", "/orders/**").hasAuthority(Permission.MANAGEMENT_READ.getAuthority())
                .antMatchers(HttpMethod.POST, "/users/**", "/orders/**").hasAuthority(Permission.MANAGEMENT_WRITE.getAuthority())
                .antMatchers(HttpMethod.DELETE, "/users/**", "/orders/**").hasAuthority(Permission.MANAGEMENT_WRITE.getAuthority())
                .antMatchers(HttpMethod.PATCH, "/users/**", "/orders/**").hasAuthority(Permission.MANAGEMENT_WRITE.getAuthority())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //security.addFilter(new AuthenticationFilter(authenticationManagerBean()));
    }




    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(service);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Value("${auth0.audience}")
//    private String audience;
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuer;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
//                JwtDecoders.fromOidcIssuerLocation(issuer);
//
//        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
//        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
//        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
//
//        jwtDecoder.setJwtValidator(withAudience);
//
//        return jwtDecoder;
//    }
}
