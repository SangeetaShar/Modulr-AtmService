package com.modulr.service;

import com.modulr.dto.Note;
import com.modulr.exception.WithdrawalAmountTranslationToNotesException;
import com.modulr.repository.NoteRepository;
import com.modulr.repository.NoteRepositoryImpl;
import com.modulr.util.NoteHelper;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@RunWith(JUnitParamsRunner.class)
public class NoteServiceImplTest {

    private NoteRepository noteRepository;

    private NoteServiceImpl noteService;

    @Before
    public void setup() {
        noteRepository = new NoteRepositoryImpl();
        noteService = new NoteServiceImpl();
    }

    @Test(expected = WithdrawalAmountTranslationToNotesException.class)
    public void translateWithdrawalAmountImpossible() throws WithdrawalAmountTranslationToNotesException {
        noteService.translateWithdrawalAmount(12L, noteRepository);
    }

    @Test(expected = WithdrawalAmountTranslationToNotesException.class)
    public void translateWithdrawalAmountInsufficientNotes() throws WithdrawalAmountTranslationToNotesException {
        noteService.translateWithdrawalAmount(1000L, noteRepository);
    }

    @Test
    @Parameters(method = "parameters")
    public void translateWithdrawalAmount( final String name, final Map<Note, Long> noteMap,
                                           final long amount,
                                           final Map<Note, Long> noteMapTranslationExpected,
                                           final boolean isErrorExpected ) throws WithdrawalAmountTranslationToNotesException {

        final Collection<Note> noteList = NoteHelper.toNoteList(noteMap);
        noteRepository.addNoteList(noteList);

        Collection<Note> noteListTranslation = null;
        try {
            noteListTranslation = noteService.translateWithdrawalAmount(amount, noteRepository);
        } catch (WithdrawalAmountTranslationToNotesException cause) {
            if (!isErrorExpected) {
                throw cause;
            }
        }

        final Map<Note, Long> noteMapTranslation = NoteHelper.toNoteMap(noteListTranslation);
        assertEquals(noteMapTranslationExpected, noteMapTranslation);
    }

    public static Object[][] parameters() {
        final Collection<Object[]> parameters = new ArrayList<>();
        for ( TranslateWithdrawalAmountTestData testData : TranslateWithdrawalAmountTestData.values()) {
            parameters.add(testData.getParameters());
        }
        return parameters.toArray(new Object[][] {});
    }


}
