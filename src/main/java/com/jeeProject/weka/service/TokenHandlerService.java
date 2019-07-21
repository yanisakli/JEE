package com.jeeProject.weka.service;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenHandlerService {

    private String token;

    public TokenHandlerService() { }

    public String getToken() {
        return this.token;
    }

    public void setToken() {
        SecureRandom random = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getEncoder();
        byte bytes[] = new byte[24];
        random.nextBytes(bytes);
        this.token = base64Encoder.encodeToString(bytes);
    }
}
