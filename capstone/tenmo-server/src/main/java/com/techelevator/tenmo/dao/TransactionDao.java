package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    String transfer(BigDecimal amount, int sender, int receiver, String transfer_type);

    void logTransfer(BigDecimal amount, int sender, int receiver, String transfer_type, String transfer_status_desc);

    TransactionDTO[] viewTransfers(int id);
}
