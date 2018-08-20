package com.modulr.service;

import com.modulr.dto.Note;
import com.modulr.exception.WithdrawalAmountTranslationToNotesException;
import com.modulr.repository.NoteRepository;

import java.util.Collection;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public interface NoteService {
    Collection<Note> translateWithdrawalAmount(long amount, NoteRepository noteRepository)
            throws WithdrawalAmountTranslationToNotesException;
}
