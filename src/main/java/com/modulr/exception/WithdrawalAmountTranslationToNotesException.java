package com.modulr.exception;

import java.text.MessageFormat;

/**
 * Error to be raised when a withdrawal amount cannot be translated to ATM
 * notes</br>
 * 
 * @author eliasbalasis
 */
public class WithdrawalAmountTranslationToNotesException extends Exception {

	public WithdrawalAmountTranslationToNotesException(final long amount) {
		super(MessageFormat.format("Withdrawal amount '{0}' cannot be translated to notes", amount));
	}

}
