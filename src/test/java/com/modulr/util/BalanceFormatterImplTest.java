package com.modulr.util;

import com.modulr.dto.AccountDto;
import com.modulr.dto.AccountDtoImpl;
import com.modulr.dto.Note;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public class BalanceFormatterImplTest {
    private static final String ACCOUNT_NUMBER = "number";
    private static final BigDecimal ACCOUNT_BALANCE = BigDecimal.valueOf(100L);

    private static final String BALANCE_TO_DISPLAY = Note.CURRENCY.getSymbol() + " 100";

    private BalanceFormatterImpl balanceFormatter;

    private AccountDto account;

    @Before
    public void setup() {
        balanceFormatter = new BalanceFormatterImpl();
        account = new AccountDtoImpl(ACCOUNT_NUMBER, ACCOUNT_BALANCE);
    }

    @Test
    public void formatBalance() {
        final String balanceToDisplay = balanceFormatter.formatBalance(account);
        assertEquals(balanceToDisplay, BALANCE_TO_DISPLAY);
    }
}
