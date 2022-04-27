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

@PreAuthorize("isAuthenticated()")
@RestController
public class TEnmoController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;

    @RequestMapping(path = "/balance/{user_id}", method = RequestMethod.GET)
    private BigDecimal getBalance(@PathVariable Long user_id){
        System.out.println("DEBUG: ");
        System.out.println(user_id);
//        try{
        return accountDao.checkBalance(user_id);
//        }catch (NullPointerException e){
//            return new BigDecimal("9999");
//        }
    }


}
