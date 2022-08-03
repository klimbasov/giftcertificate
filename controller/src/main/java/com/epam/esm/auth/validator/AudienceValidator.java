package com.epam.esm.auth.validator;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final String AUDIENCE_NAME = "smth";
    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        OAuth2TokenValidatorResult result = OAuth2TokenValidatorResult.failure(
                new OAuth2Error("invalid token", "The required audience is missing", null)
        );
        if(token.getAudience().contains(AUDIENCE_NAME)){
            result = OAuth2TokenValidatorResult.success();
        }
        return result;
    }
}
