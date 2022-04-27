package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal checkBalance(Long user_id) {
        BigDecimal balance = new BigDecimal("0.00");

        String sql = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, user_id);

        if (results.next()) {
            balance = results.getBigDecimal("balance");
        }

        return balance;
    }

    private Account accountMapper(SqlRowSet results) {
        Account account = new Account();

        account.setBalance(results.getBigDecimal("balance"));
        account.setUserId(results.getLong("user_id"));
        account.setAccountId(results.getLong("account_id"));

        return account;
    }

}
