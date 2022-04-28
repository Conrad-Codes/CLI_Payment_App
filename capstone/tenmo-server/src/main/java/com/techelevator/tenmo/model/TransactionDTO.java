package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.UserDao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.security.Principal;

public class TransactionDTO {

    private UserDao userDao;
    private Principal principal;

    @NotEmpty
    @Positive
    private BigDecimal amount;

    //    @AssertFalse(userDao.findIdByUsername(principal.getName()))
    @NotEmpty
    private int receiverID;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
}
