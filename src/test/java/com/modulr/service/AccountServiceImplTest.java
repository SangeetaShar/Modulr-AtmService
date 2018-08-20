package com.modulr.service;

import com.modulr.dto.AccountDto;
import com.modulr.dto.AccountDtoImpl;
import com.modulr.exception.LowBalanceException;
import com.modulr.exception.NoAccountException;
import com.modulr.repository.AccountRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest  {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final String ACCOUNT_NUMBER = "number";
    private static final BigDecimal ACCOUNT_BALANCE = BigDecimal.ZERO;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountDtoImpl account;

    @InjectMocks
    private AccountServiceImpl accountService = new AccountServiceImpl();

    @Test
    public void getAccount() throws NoAccountException {
        when(accountRepository.getAccount(ACCOUNT_NUMBER)).thenReturn(this.account);
        final AccountDto account = accountService.getAccount(ACCOUNT_NUMBER);
        verify(accountRepository).getAccount(same(ACCOUNT_NUMBER));
        assertSame( account, this.account);
    }

    @Test
    public void getAccountThrowsNoAccountException() throws NoAccountException {
        exception.expect(NoAccountException.class);
        when(accountRepository.getAccount(ACCOUNT_NUMBER)).thenThrow(new NoAccountException(ACCOUNT_NUMBER));
        final AccountDto account = accountService.getAccount(ACCOUNT_NUMBER);
        verify(accountRepository).getAccount(same(ACCOUNT_NUMBER));
    }


    @Test
    public void withdrawAmount() throws LowBalanceException, NoAccountException {
        accountService.withdrawAmount(ACCOUNT_NUMBER,ACCOUNT_BALANCE.longValue());
        verify(accountRepository).withdrawAmount(same(ACCOUNT_NUMBER),eq(ACCOUNT_BALANCE.longValue()));
    }

}
