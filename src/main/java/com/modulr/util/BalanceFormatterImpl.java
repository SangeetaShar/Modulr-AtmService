package com.modulr.util;

import com.modulr.dto.AccountDto;
import com.modulr.dto.Note;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class BalanceFormatterImpl implements BalanceFormatter {

	@Override
	public String formatBalance(final AccountDto account) {
		return MessageFormat.format("{0} {1}",
				new Object[] {Note.CURRENCY.getSymbol(),account.getAccountBalance()});
	}
}
