package com.techelevator.tenmo.dao;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public BigDecimal checkBalance(int userID) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, userID);

        BigDecimal balance= new BigDecimal("0.00");

        if(results.next()){
            balance = results.getBigDecimal("balance");
        }
        return balance;
    }
}
