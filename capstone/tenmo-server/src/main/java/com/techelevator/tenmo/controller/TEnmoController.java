package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@PreAuthorize("isAuthenticated()")
@RestController
public class TEnmoController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;

    @RequestMapping(path = "/balance/{user_id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long user_id){
        return accountDao.checkBalance(user_id);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public Map<Long, String> listUsers() {
        return accountDao.listUsers();
    }

}
