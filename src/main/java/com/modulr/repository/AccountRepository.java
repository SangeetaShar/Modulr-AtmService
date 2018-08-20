package com.modulr.repository;

import com.modulr.dto.AccountDtoImpl;
import com.modulr.exception.LowBalanceException;
import com.modulr.exception.NoAccountException;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public interface AccountRepository {

    AccountDtoImpl getAccount(final String accountNumber) throws NoAccountException;

    void withdrawAmount(final String accountNumber, final long withdrawalAmount)
            throws NoAccountException, LowBalanceException;
}
