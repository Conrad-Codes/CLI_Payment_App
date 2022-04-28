package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    String transfer(BigDecimal amount, int sender, int receiver);

    void logTransfer(BigDecimal amount, int sender, int receiver);

}
