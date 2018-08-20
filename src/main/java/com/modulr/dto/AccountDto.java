package com.modulr.dto;

import java.math.BigDecimal;

/**
 * Created by Sangeeta Sharma on 16/08/2018.
 */
public interface AccountDto {
    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    BigDecimal getAccountBalance();

    void setAccountBalance(BigDecimal accountBalance);
}
