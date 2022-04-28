package com.techelevator.tenmo.dao;

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
    public String transfer(BigDecimal amount, int receiver, int sender, String transfer_type) {

        if (receiver == sender) {
            return "Sender cannot be receiver.";
        }

        BigDecimal senderBalance = accountDao.checkBalance(sender);
        BigDecimal receiverBalance = accountDao.checkBalance(receiver);

        if (!(senderBalance.compareTo(amount) >= 0)) {
            return "Insufficient funds.";
        }

        senderBalance = senderBalance.subtract(amount);
        receiverBalance = receiverBalance.add(amount);

        String sql = "START TRANSACTION;" +
                "UPDATE account SET balance = ? WHERE user_id = ?;" +
                "UPDATE account SET balance = ? WHERE user_id = ?; " +
                "COMMIT;";

        jdbcTemplate.update(sql, senderBalance, sender, receiverBalance, receiver);

        logTransfer(amount, receiver, sender, transfer_type, "Approved");

        return "\nTransaction complete.\nNew balance: " + senderBalance;
    }

    @Override
    public void logTransfer(BigDecimal amount, int receiver, int sender, String transfer_type, String transfer_status_desc) {

        int account_from = userDao.findAccountIdByUserId(receiver);
        int account_to = userDao.findAccountIdByUserId(sender);

        String sql = "SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = ?;";
        Integer transfer_type_id = jdbcTemplate.queryForObject(sql, Integer.class, transfer_type);

        sql = "SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = ?;";
        Integer transfer_status_id = jdbcTemplate.queryForObject(sql, Integer.class, transfer_status_desc);

        sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, transfer_type_id, transfer_status_id, account_from, account_to, amount);

    }
}
