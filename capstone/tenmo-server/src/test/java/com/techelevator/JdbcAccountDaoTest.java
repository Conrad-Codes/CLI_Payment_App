package com.techelevator;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTest extends BaseDaoTests{

    private static final Account ACCOUNT_1 = new Account(new BigDecimal("1000.00"), 111, 1111 );
    private static final Account ACCOUNT_2 = new Account(new BigDecimal("2000.00"), 222, 2222 );
    private static final Account ACCOUNT_3 = new Account(new BigDecimal("3000.00"), 333, 3333 );

    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao sut;

    @Before
    public void setup() {
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void check_balance_returns_correct_balance(){
        BigDecimal balance = sut.checkBalance(1111);

        Assert.assertEquals(new BigDecimal("1000.00"),balance);
    }

}
