package com.modulr.service;

import com.modulr.dto.AccountDto;
import com.modulr.exception.LowBalanceException;
import com.modulr.exception.NoAccountException;

/**
 * Created by Sangeeta Sharma on 16/08/2018.
 */
public interface AccountService {

    void withdrawAmount(String accountNumber, long withdrawalAmount) throws LowBalanceException, NoAccountException;

    AccountDto getAccount(String accountNumber) throws NoAccountException;
}
