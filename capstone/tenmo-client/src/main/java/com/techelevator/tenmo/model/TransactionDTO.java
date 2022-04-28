package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.security.Principal;

public class TransactionDTO {

    private BigDecimal amount;
    private int receiverID;
    private String transfer_type;

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

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }
}
