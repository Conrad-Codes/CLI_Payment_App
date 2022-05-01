package com.techelevator;

import com.techelevator.tenmo.model.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransactionDTOTests {

    private TransactionDTO tDTO;

    @Before
    public void setup(){
        tDTO = new TransactionDTO(1, "Send", "Approved", "FromUser", "ToUser", 111, new BigDecimal("150.00"));
    }


    @Test
    public void test_transfer_id_methods(){
        Assert.assertEquals(1, tDTO.getTransfer_id());

        tDTO.setTransfer_id(2);

        Assert.assertEquals(2, tDTO.getTransfer_id());
    }

    @Test
    public void test_transfer_status_methods(){
        Assert.assertEquals("Approved", tDTO.getTransfer_status_desc());

        tDTO.setTransfer_status_desc("Rejected");

        Assert.assertEquals("Rejected", tDTO.getTransfer_status_desc());
    }

    @Test
    public void test_account_from_methods(){
        Assert.assertEquals("FromUser", tDTO.getAccount_from());

        tDTO.setAccount_from("Nobody");

        Assert.assertEquals("Nobody", tDTO.getAccount_from());
    }



}
