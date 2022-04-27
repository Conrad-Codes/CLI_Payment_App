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

public class TEnmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;
    private User user = new User();
    private AuthenticatedUser authenticatedUser = new AuthenticatedUser();
    private Principal principal;

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

    public BigDecimal getBalance(Long id) {

        System.out.println("DEBUG: ");
        System.out.println(id);

        BigDecimal balance = new BigDecimal("0.00");

        try {
            balance = restTemplate.exchange(
                    API_BASE_URL + "balance/" + id,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    BigDecimal.class
            ).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.printStackTrace();
        }

        return balance;
    }

}
