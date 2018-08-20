package com.modulr.exception;

import java.math.BigDecimal;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public class LowBalanceException extends RuntimeException {

    public LowBalanceException(final String accountNumber, final BigDecimal balance,
                               final long withdrawalAmount) {
        super(String.format("Account '{}' does not have enough balance. Requested '{}' but was only '{}'", //
                new Object[] {accountNumber, withdrawalAmount, balance} ));
    }
}
