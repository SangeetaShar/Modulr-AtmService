package com.modulr.service;

import com.modulr.dto.Note;
import com.modulr.exception.*;
import com.modulr.repository.NoteRepository;
import com.modulr.util.BalanceFormatter;
import com.modulr.util.NoteHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@Service
public class ATMServiceImpl implements ATMService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ATMServiceImpl.class);

    /**
     * The underlying accounts access service</br>
     */
    @Autowired
    private AccountService accountService;

    /**
     * The underlying notes service</br>
     */
    @Autowired
    private NoteService noteService;

    /**
     * The notes persistent storage service</br>
     */
    @Autowired
    private NoteRepository noteRepository;

    /**
     * The service to generate displayable balance</br>
     */
    @Autowired
    private BalanceFormatter balanceFormatter;

    /**
     * The minimum withdrawal amountParameter</br>
     */
    @Value("${minimumWithdrawalAmount:20}")
    private long minimumWithdrawalAmount;
    /**
     * The maximum withdrawal amountParameter</br>
     */
    @Value("${maximumWithdrawalAmount:250}")
    private long maximumWithdrawalAmount;


    /**
     * This method is to replenish the ATM machine with the currency Note.
     * @param noteList
     */
    @Override
    public void replenish(Collection<Note> noteList) {
        LOGGER.info("Replenishing ATM with notes: {}",NoteHelper.toNoteMap(noteList));
        noteRepository.addNoteList(noteList);
    }

    /**
     * This method returns the formatted outstanding balance in the account.
     * @param accountNumber
     * @return
     * @throws NoAccountException
     */
    @Override
    public String checkBalance(String accountNumber) throws NoAccountException {
        LOGGER.info("Checking balance of account '{}'",accountNumber);
        return balanceFormatter.formatBalance(accountService.getAccount(accountNumber));
    }

    /**
     * This method is to withdraw the specified amount from the account. It returns the collection of notes that makes up the amount.
     * @param accountNumber
     * @param withdrawalAmount
     * @return
     * @throws NoAccountException
     * @throws LowBalanceException
     * @throws ATMException
     * @throws WithdrawalAmountNotAllowedException
     * @throws WithdrawalAmountTranslationToNotesException
     */
    @Override
    public Collection<Note> withdrawAmount(String accountNumber, long withdrawalAmount) throws NoAccountException, LowBalanceException, ATMException, WithdrawalAmountNotAllowedException, WithdrawalAmountTranslationToNotesException {
        LOGGER.info("Withdrawing amountParameter of '{}' from account '{}'...",withdrawalAmount, accountNumber);
        if (withdrawalAmount < minimumWithdrawalAmount ||
                withdrawalAmount > maximumWithdrawalAmount) {
            final WithdrawalAmountNotAllowedException error =
                    new WithdrawalAmountNotAllowedException(withdrawalAmount, minimumWithdrawalAmount, maximumWithdrawalAmount);
            LOGGER.error(error.getMessage());
            throw error;
        }
        final Collection<Note> noteList = noteService.translateWithdrawalAmount(withdrawalAmount,noteRepository);
        noteRepository.tryRemoveNoteList(noteList);
        accountService.withdrawAmount(accountNumber,withdrawalAmount);
        noteRepository.removeNoteList(noteList);
        return noteList;
    }
}
