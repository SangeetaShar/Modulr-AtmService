package com.modulr.repository;

import com.modulr.dto.Note;
import com.modulr.exception.ATMException;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public interface NoteRepository {

    void addNoteList(final Collection<Note> noteList);

    void tryRemoveNoteList(final Collection<Note> noteList) throws ATMException;

    void removeNoteList(final Collection<Note> noteList) throws ATMException;

    Map<Note, Long> getDeposit();

    long getCountOfNotes(final Note note);
}
