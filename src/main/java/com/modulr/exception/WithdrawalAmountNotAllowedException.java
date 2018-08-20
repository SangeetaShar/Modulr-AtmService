package com.modulr.exception;

import java.text.MessageFormat;

/**
 * Error to be raised when a withdrawal amount cannot be translated to ATM
 * notes</br>
 * 
 * @author eliasbalasis
 */
public class WithdrawalAmountNotAllowedException extends RuntimeException {

	public WithdrawalAmountNotAllowedException(final long amount, final long minimum, final long maximum) {
		super(MessageFormat.format( "Withdrawal amount '{0}' is not within the allowed range ({1}-{2})",
				new Object[] {amount,minimum, maximum}));
	}

}
