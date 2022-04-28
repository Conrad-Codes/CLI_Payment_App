package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.temporal.Temporal;
import java.util.*;

public class TEnmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

//    private HttpEntity<User> makeUserEntity(User user) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(authToken);
//        HttpEntity<User> entity = new HttpEntity<>(User, headers);
//
//        return entity;
//    }

    private HttpEntity<Void> makeAuthEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }

    public BigDecimal getBalance(Long user_id) {

        BigDecimal balance = new BigDecimal("0.00");

        try {
            balance = restTemplate.exchange(
                    API_BASE_URL + "balance/" + user_id,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    BigDecimal.class
            ).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public List<User> listUsers(Long user_id) {
        List<User> users = new ArrayList<>();

        users = restTemplate.exchange(
          API_BASE_URL + "users",
          HttpMethod.GET,
          makeAuthEntity(),
          List.class
        ).getBody();

        return users;
    }

}
