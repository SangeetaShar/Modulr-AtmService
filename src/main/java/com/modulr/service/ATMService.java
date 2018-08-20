package com.modulr.service;

import com.modulr.dto.Note;
import com.modulr.exception.*;

import java.util.Collection;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public interface ATMService {

    void replenish(Collection<Note> noteList);

    String checkBalance(String accountNumber) throws NoAccountException;

    Collection<Note> withdrawAmount(String accountNumber,long withdrawalAmount) throws NoAccountException,
            LowBalanceException, ATMException, WithdrawalAmountNotAllowedException, WithdrawalAmountTranslationToNotesException;
}
