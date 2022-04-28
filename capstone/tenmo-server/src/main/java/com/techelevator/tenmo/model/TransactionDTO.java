package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.UserDao;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.security.Principal;

public class TransactionDTO {

    private UserDao userDao;
    private Principal principal;

    @NotEmpty
    private String transfer_type;

    @Min(value = 1, message = "Amount must be greater than 0")
    @Positive
    private BigDecimal amount;

    @NotNull
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

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }
}
