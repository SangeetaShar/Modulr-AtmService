package com.modulr.service;

import com.modulr.dto.AccountDto;
import com.modulr.exception.LowBalanceException;
import com.modulr.exception.NoAccountException;
import com.modulr.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    /**
     *  This method is to withdraw the supplied money from the given account number
     * @param accountNumber
     * @param withdrawalAmount
     * @throws LowBalanceException
     * @throws NoAccountException
     */
    @Override
    public void withdrawAmount(String accountNumber, long withdrawalAmount) throws LowBalanceException, NoAccountException {
        LOGGER.info("Withdrawing amountParameter of '{}' from account with number '{}'", withdrawalAmount,
                accountNumber);
        accountRepository.withdrawAmount(accountNumber, withdrawalAmount);
    }

    /**
     * This method fetched the information about the given account number.
     * @param accountNumber
     * @return
     * @throws NoAccountException
     */
    @Override
    public AccountDto getAccount(String accountNumber) throws NoAccountException {
        LOGGER.info("Retrieving account with number '{}'", accountNumber);
        return accountRepository.getAccount(accountNumber);
    }
}
