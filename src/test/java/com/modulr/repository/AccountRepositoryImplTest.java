package com.modulr.repository;

import com.modulr.dto.AccountDto;
import com.modulr.exception.LowBalanceException;
import com.modulr.exception.NoAccountException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public class AccountRepositoryImplTest {

    private AccountRepositoryImpl accountRepository;

    @Before
    public void setup() {
        accountRepository = AccountRepositoryImpl.create();
    }

    @Test(expected =  NoAccountException.class )
    public void getAccountNotFound() throws NoAccountException {
        accountRepository.getAccount("unknown");
    }

    @Test
    public void getAccount1() throws NoAccountException {
        final AccountDto account = accountRepository.getAccount(AccountRepositoryImpl.ACCOUNTNUM1);
        assertEquals(account.getAccountNumber(), AccountRepositoryImpl.ACCOUNTNUM1);
        assertEquals(account.getAccountBalance(), AccountRepositoryImpl.ACCOUNTBAL1);
    }

    @Test
    public void getAccount2() throws NoAccountException {
        final AccountDto account = accountRepository.getAccount(AccountRepositoryImpl.ACCOUNTNUM2);
        assertEquals(account.getAccountNumber(), AccountRepositoryImpl.ACCOUNTNUM2);
        assertEquals(account.getAccountBalance(), AccountRepositoryImpl.ACCOUNTBAL2);
    }

    @Test
    public void getAccount3() throws NoAccountException {
        final AccountDto account = accountRepository.getAccount(AccountRepositoryImpl.ACCOUNTNUM3);
        assertEquals(account.getAccountNumber(), AccountRepositoryImpl.ACCOUNTNUM3);
        assertEquals(account.getAccountBalance(), AccountRepositoryImpl.ACCOUNTBAL3);
    }

    @Test
    public void withdrawAmount() throws NoAccountException, LowBalanceException {
        accountRepository.withdrawAmount(AccountRepositoryImpl.ACCOUNTNUM1, 500L);
        final AccountDto account = accountRepository.getAccount(AccountRepositoryImpl.ACCOUNTNUM1);
        assertEquals( account.getAccountBalance(),
                AccountRepositoryImpl.ACCOUNTBAL1.subtract(BigDecimal.valueOf(500L)));
    }

    @Test(expected =  LowBalanceException.class)
    public void withdrawAmountNotEnoughBalance() throws NoAccountException, LowBalanceException {
        accountRepository.withdrawAmount(AccountRepositoryImpl.ACCOUNTNUM1, 5000L);
    }
}
