package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    String transfer(BigDecimal amount, Long sender, Long receiver);

}
