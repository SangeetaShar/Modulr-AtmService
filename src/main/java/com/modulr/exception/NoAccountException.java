package com.modulr.exception;

import java.text.MessageFormat;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public class NoAccountException extends RuntimeException {

    public NoAccountException(final String accountNumber) {
        super(MessageFormat.format("Wrong account number '{0}'", accountNumber));
    }
}
