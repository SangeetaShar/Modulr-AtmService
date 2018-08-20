package com.modulr.dto;

import java.math.BigDecimal;

/**
 * Created by Sangeeta Sharma on 16/08/2018.
 */
public class AccountDtoImpl implements AccountDto {

    private String accountNumber;
    private BigDecimal accountBalance;

    public AccountDtoImpl(String accountNumber, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}
