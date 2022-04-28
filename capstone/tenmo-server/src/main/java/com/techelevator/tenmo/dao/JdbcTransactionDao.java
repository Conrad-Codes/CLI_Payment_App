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

    @Override
    public String transfer(BigDecimal amount, int receiver, int sender) {

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

        System.out.println(senderBalance);
        System.out.println(receiverBalance);

        String sql = "START TRANSACTION;" +
                "UPDATE account SET balance = ? WHERE user_id = ?;" +
                "UPDATE account SET balance = ? WHERE user_id = ?; " +
                "COMMIT;";

        jdbcTemplate.update(sql, senderBalance, sender, receiverBalance, receiver);



        return "\nTransaction complete.\nNew balance: " + senderBalance;
    }
}
