package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountDao {

    BigDecimal checkBalance(Long user_id);

    Map<Long, String> listUsers();

}
