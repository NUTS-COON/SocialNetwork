package ru.nutscoon.sn.api.service.auth;

import ru.nutscoon.sn.core.repository.PersonTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    private final PersonTokenRepository personTokenRepository;


    @Autowired
    public TokenAuthenticationManager(PersonTokenRepository personTokenRepository) {
        this.personTokenRepository = personTokenRepository;
    }


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        String token = (String) authentication.getPrincipal();

        if (!isAuthenticated(token))
        {
            throw new BadCredentialsException("The API key was not found "
                    + "or not the expected value.");
        }

        authentication.setAuthenticated(true);
        return authentication;
    }

    private boolean isAuthenticated(String token) {
        if(token == null || token.isEmpty()){
            return false;
        }

        return personTokenRepository.existsByToken(token);
    }
}