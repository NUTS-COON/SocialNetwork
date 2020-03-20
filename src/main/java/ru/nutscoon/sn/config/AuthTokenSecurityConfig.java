package ru.nutscoon.sn.config;

import ru.nutscoon.sn.api.service.auth.PreAuthTokenHeaderFilter;
import ru.nutscoon.sn.api.service.auth.TokenAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@Order(1)
public class AuthTokenSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationManager tokenAuthenticationManager;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        PreAuthTokenHeaderFilter filter = new PreAuthTokenHeaderFilter();
        filter.setAuthenticationManager(tokenAuthenticationManager);

        httpSecurity.
                antMatcher("/api/**")
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(filter)
                .addFilterBefore(new ExceptionTranslationFilter(
                                new Http403ForbiddenEntryPoint()),
                        filter.getClass()
                )
                .authorizeRequests()
                .antMatchers("/api/auth/login", "/api/auth/register", "/api/swagger-ui.html", "/api/auth/resetPassword").permitAll()
                .anyRequest()
                .authenticated();

    }
}
