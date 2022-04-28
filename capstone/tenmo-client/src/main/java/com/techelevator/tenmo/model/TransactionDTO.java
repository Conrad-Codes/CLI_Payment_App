package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.security.Principal;

public class TransactionDTO {



    private BigDecimal amount;
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
