package com.modulr.service;

import com.modulr.dto.AccountDto;
import com.modulr.dto.Note;
import com.modulr.exception.*;
import com.modulr.repository.NoteRepository;
import com.modulr.util.BalanceFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ATMServiceImplTest {


    private static final String ACCOUNT_NUMBER = "number";
    private static final long WITHDRAWAL_AMOUNT = 100L;
    private static final long WITHDRAWAL_AMOUNT_TOO_SMALL = 10L;
    private static final long WITHDRAWAL_AMOUNT_TOO_LARGE = 300L;

    @Mock
    private AccountService accountService;
    @Mock
    private NoteService noteService;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private BalanceFormatter balanceFormatter;
    @Mock
    private AccountDto account;

    @InjectMocks
    private ATMServiceImpl service = new ATMServiceImpl();

    private Collection<Note> withdrawalNoteList;

    @Before
    public void setup() throws NoAccountException, WithdrawalAmountTranslationToNotesException {
        ReflectionTestUtils.setField(service,"minimumWithdrawalAmount",20);
        ReflectionTestUtils.setField(service,"maximumWithdrawalAmount",250);
    }

    @Test
    public void replenish() {
        final Collection<Note> noteList = Arrays.asList( Note.values());
        service.replenish(noteList);
    }

    @Test
    public void checkBalance() throws NoAccountException {

        when(accountService.getAccount(ACCOUNT_NUMBER)).thenReturn(this.account);

        service.checkBalance(ACCOUNT_NUMBER);

        final InOrder inOrder = Mockito.inOrder(accountService,balanceFormatter);
        inOrder.verify( accountService).getAccount(same(ACCOUNT_NUMBER));
        inOrder.verify( balanceFormatter).formatBalance(same(account));
    }

    @Test
    public void withdrawAmount() throws NoAccountException,LowBalanceException, ATMException,
            WithdrawalAmountTranslationToNotesException, WithdrawalAmountNotAllowedException {
        when(accountService.getAccount(ACCOUNT_NUMBER)).thenReturn(this.account);
        when(noteService.translateWithdrawalAmount(eq(WITHDRAWAL_AMOUNT),same(noteRepository))).thenReturn(withdrawalNoteList);

        service.withdrawAmount( ACCOUNT_NUMBER, WITHDRAWAL_AMOUNT );

        final InOrder inOrder = Mockito.inOrder(noteService,noteRepository,accountService);
        inOrder.verify(noteService ).translateWithdrawalAmount(eq(WITHDRAWAL_AMOUNT),same(noteRepository));
        inOrder.verify(noteRepository).tryRemoveNoteList(same(withdrawalNoteList));
        inOrder.verify(accountService).withdrawAmount(same(ACCOUNT_NUMBER), eq(WITHDRAWAL_AMOUNT));
        inOrder.verify(noteRepository).removeNoteList(same(withdrawalNoteList));
    }

    @Test(expected = WithdrawalAmountNotAllowedException.class)
    public void withdrawAmountTooSmall() throws NoAccountException, LowBalanceException, ATMException,
            WithdrawalAmountTranslationToNotesException,WithdrawalAmountNotAllowedException {
        service.withdrawAmount(ACCOUNT_NUMBER,WITHDRAWAL_AMOUNT_TOO_SMALL);
    }

    @Test(expected = WithdrawalAmountNotAllowedException.class)
    public void withdrawAmountTooBig() throws NoAccountException,LowBalanceException,ATMException,
            WithdrawalAmountTranslationToNotesException,WithdrawalAmountNotAllowedException {
        service.withdrawAmount(ACCOUNT_NUMBER,WITHDRAWAL_AMOUNT_TOO_LARGE );
    }
}
