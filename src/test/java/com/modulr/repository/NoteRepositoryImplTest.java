package com.modulr.repository;

import com.modulr.dto.Note;
import com.modulr.exception.ATMException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public class NoteRepositoryImplTest {
    private NoteRepositoryImpl noteRepository;

    @Before
    public void setup() {
        noteRepository = new NoteRepositoryImpl();
    }

    @Test
    public void addNoteList() {
        final Collection<Note> noteList =Arrays.asList(Note.values());
        noteRepository.addNoteList(noteList);
        final Map<Note, Long> noteMap = noteRepository.getDeposit();
        assertEquals(noteMap.size(),noteList.size());
        for (Note note : Note.values()) {
            assertEquals(noteMap.get(note),Long.valueOf(1));
        }
    }

    @Test
    public void addNoteListRepeatedly() {
        final Collection<Note> noteList = Arrays.asList(Note.values());
        noteRepository.addNoteList(noteList);
        noteRepository.addNoteList(noteList);
        final Map<Note, Long> noteMap = noteRepository.getDeposit();
        assertEquals(noteMap.size(),noteList.size());
        for (Note note : Note.values()) {
            assertEquals(noteMap.get(note),Long.valueOf(2));
        }
    }

    @Test
    public void tryRemoveNoteList() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList(Note.FIVE,Note.TEN,Note.TWENTY,Note.FIFTY );
        noteRepository.tryRemoveNoteList(noteList);
    }

    @Test(expected = ATMException.class )
    public void tryRemoveNoteListNotEnoughFIVE() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList(Note.FIVE,Note.FIVE);
        noteRepository.tryRemoveNoteList(noteList);
    }

    @Test(expected = ATMException.class )
    public void tryRemoveNoteListNotEnoughTEN() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList( Note.TEN,Note.TEN );
        noteRepository.tryRemoveNoteList(noteList);
    }

    @Test(expected = ATMException.class )
    public void tryRemoveNoteListNotEnoughTWENTY() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList( Note.TWENTY, Note.TWENTY );
        noteRepository.tryRemoveNoteList(noteList);
    }

    @Test(expected = ATMException.class )
    public void tryRemoveNoteListNotEnoughFIFTY() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList( Note.FIFTY, Note.FIFTY);
        noteRepository.tryRemoveNoteList(noteList);
    }

    @Test
    public void removeNoteListFIVE() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList(Note.FIVE);
        noteRepository.removeNoteList(noteList);
        final Map<Note, Long> noteMap = noteRepository.getDeposit();
        assertNull(noteMap.get(Note.FIVE));
        assertNotNull(noteMap.get(Note.TEN));
        assertNotNull(noteMap.get(Note.TWENTY));
        assertNotNull(noteMap.get(Note.FIFTY));
    }

    @Test
    public void removeNoteListTEN() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList(Note.TEN);
        noteRepository.removeNoteList(noteList);
        final Map<Note, Long> noteMap = noteRepository.getDeposit();
        assertNotNull(noteMap.get(Note.FIVE));
        assertNull(noteMap.get(Note.TEN));
        assertNotNull(noteMap.get(Note.TWENTY));
        assertNotNull(noteMap.get(Note.FIFTY));
    }

    @Test
    public void removeNoteListTWENTY() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList(Note.TWENTY);
        noteRepository.removeNoteList(noteList);
        final Map<Note, Long> noteMap = noteRepository.getDeposit();
        assertNotNull(noteMap.get(Note.FIVE));
        assertNotNull(noteMap.get(Note.TEN));
        assertNull(noteMap.get(Note.TWENTY));
        assertNotNull(noteMap.get(Note.FIFTY));
    }

    @Test
    public void removeNoteListFIFTY() throws ATMException {
        addNoteList();
        final Collection<Note> noteList = Arrays.asList(Note.FIFTY);
        noteRepository.removeNoteList(noteList);
        final Map<Note, Long> noteMap = noteRepository.getDeposit();
        assertNotNull(noteMap.get(Note.FIVE));
        assertNotNull(noteMap.get(Note.TEN));
        assertNotNull(noteMap.get(Note.TWENTY));
        assertNull(noteMap.get(Note.FIFTY));
    }

    @Test
    public void getCountOfNotes() throws ATMException {
        addNoteList();
        assertEquals(1, noteRepository.getCountOfNotes(Note.FIVE));
        final Collection<Note> noteList = Arrays.asList(Note.FIVE);
        noteRepository.addNoteList(noteList);
        assertEquals(2, noteRepository.getCountOfNotes(Note.FIVE));
    }
}
