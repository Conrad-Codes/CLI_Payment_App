package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Override
    public String transfer(BigDecimal amount, int account_to, int account_from, String transfer_type_desc) {

        if (account_to == account_from) {
            return "Sender cannot be receiver.";
        }

        BigDecimal account_from_balance = accountDao.checkBalance(account_from);
        BigDecimal account_to_balance = accountDao.checkBalance(account_to);

        if (!(account_from_balance.compareTo(amount) >= 0)) {
            return "Insufficient funds.";
        }

        account_from_balance = account_from_balance.subtract(amount);
        account_to_balance = account_to_balance.add(amount);

        String sql = "START TRANSACTION;" +
                "UPDATE account SET balance = ? WHERE user_id = ?;" +
                "UPDATE account SET balance = ? WHERE user_id = ?; " +
                "COMMIT;";

        jdbcTemplate.update(sql, account_from_balance, account_from, account_to_balance, account_to);

        logTransfer(amount, account_to, account_from, transfer_type_desc, "Approved");

        return "\nTransaction complete.\nNew balance: " + account_from_balance;
    }

    @Override
    public void logTransfer(BigDecimal amount, int receiver_id, int sender_id, String transfer_type_desc, String transfer_status_desc) {

        int account_from = userDao.findAccountIdByUserId(sender_id);
        int account_to = userDao.findAccountIdByUserId(receiver_id);


        String sql = "SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = ?;";
        Integer transfer_type_id = jdbcTemplate.queryForObject(sql, Integer.class, transfer_type_desc);

        sql = "SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = ?;";
        Integer transfer_status_id = jdbcTemplate.queryForObject(sql, Integer.class, transfer_status_desc);

        sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, transfer_type_id, transfer_status_id, account_from, account_to, amount);

    }

    @Override
    public TransactionDTO[] viewTransfers(int id) {
        String sql = "SELECT  FROM transfer" +
        "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id" +
        "Join transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id" +
        "JOIN account ON account.account_id = transfer.account_from WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        //CODE FOR STUFF



        return null;
    }

//    public TransactionDTO transferMapper(SqlRowSet results){
//        TransactionDTO transactionDTO = new TransactionDTO();
//
//        transactionDTO.setTransfer_id(results.getInt("transfer_id"));
//        transactionDTO.setAccount_from(results.);
//
//    }
}
