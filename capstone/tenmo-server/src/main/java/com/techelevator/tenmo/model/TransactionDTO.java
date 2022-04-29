package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.UserDao;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.security.Principal;

public class TransactionDTO {

    private UserDao userDao;
    private Principal principal;

    private int transfer_id;

    @NotEmpty
    private String transfer_type_desc;

    String transfer_status_desc;

    private int account_from;

    @NotNull
    private int account_to;

    @Min(value = 1, message = "Amount must be greater than 0")
    @Positive
    private BigDecimal amount;


    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public String getTransfer_status_desc() {
        return transfer_status_desc;
    }

    public void setTransfer_status_desc(String transfer_status_desc) {
        this.transfer_status_desc = transfer_status_desc;
    }

    public int getAccount_from() {
        return account_from;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getAccount_to() {
        return account_to;
    }

    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }

    public String getTransfer_type_desc() {
        return transfer_type_desc;
    }

    public void setTransfer_type_desc(String transfer_type_desc) {
        this.transfer_type_desc = transfer_type_desc;
    }
}
