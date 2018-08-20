package com.modulr.repository;

import com.modulr.dto.AccountDtoImpl;
import com.modulr.exception.LowBalanceException;
import com.modulr.exception.NoAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    static final String ACCOUNTNUM1 = "01001";
    static final String ACCOUNTNUM2 = "01002";
    static final String ACCOUNTNUM3 = "01003";
    static final BigDecimal ACCOUNTBAL1 = BigDecimal.valueOf(2738.59d);
    static final BigDecimal ACCOUNTBAL2 = BigDecimal.valueOf(23.00d);
    static final BigDecimal ACCOUNTBAL3 = BigDecimal.valueOf(0);
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryImpl.class);
    private final Map<String, BigDecimal> accountBalanceMap = new LinkedHashMap<>();

    private AccountRepositoryImpl() {
        accountBalanceMap.put(ACCOUNTNUM1, ACCOUNTBAL1);
        accountBalanceMap.put(ACCOUNTNUM2, ACCOUNTBAL2);
        accountBalanceMap.put(ACCOUNTNUM3, ACCOUNTBAL3);
    }

    static AccountRepositoryImpl create() {
        return new AccountRepositoryImpl();
    }

    @Override
    public AccountDtoImpl getAccount(final String accountNumber) throws NoAccountException {
        LOGGER.info("Retrieving account with number '{}'", accountNumber);
        final BigDecimal accountBalance = accountBalanceMap.get(accountNumber);
        if (accountBalance == null) {
            LOGGER.info("Account with number '{}' does not exist", accountNumber);
            throw new NoAccountException(accountNumber);
        }
        final AccountDtoImpl account = new AccountDtoImpl(accountNumber, accountBalance);
        LOGGER.info("Account with number '{}' was found", accountNumber);
        return account;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.modulr.java.test.eliasbalasis.AccountRepository#withdrawAmount(java.
     * lang.String, long)
     */
    @Override
    public void withdrawAmount(final String accountNumber, final long withdrawalAmount)
            throws NoAccountException, LowBalanceException {
        LOGGER.info("Withdrawing amountParameter of '{}' from account with number '{}'",
                withdrawalAmount, accountNumber);
        final AccountDtoImpl account = getAccount(accountNumber);
        final BigDecimal accountBalance = account.getAccountBalance();
        if (accountBalance.compareTo(BigDecimal.valueOf(withdrawalAmount)) < 0) {
            LOGGER.info("Account with number '{}' does not have enough balance", accountNumber);
            throw new LowBalanceException(accountNumber, accountBalance, withdrawalAmount);
        }
        final BigDecimal newAccountBalance = accountBalance.subtract(BigDecimal.valueOf(withdrawalAmount));
        accountBalanceMap.put(accountNumber, newAccountBalance);
        LOGGER.info("Amount of '{}' was withdrawn from account with number '{}'", withdrawalAmount, accountNumber);
    }
}
