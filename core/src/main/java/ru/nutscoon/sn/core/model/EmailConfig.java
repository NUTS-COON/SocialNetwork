package ru.nutscoon.sn.core.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {
    @Value("${emailConfig.username}")
    private String username;
    @Value("${emailConfig.password}")
    private String password;
    @Value("${emailConfig.from}")
    private String from;
    @Value("${emailConfig.host}")
    private String host;
    @Value("${emailConfig.port}")
    private String port;


    public EmailConfig() {

    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFrom() {
        return from;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

}
