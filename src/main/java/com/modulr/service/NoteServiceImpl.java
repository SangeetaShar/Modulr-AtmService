package com.modulr.service;

import com.modulr.dto.Note;
import com.modulr.exception.ATMException;
import com.modulr.exception.WithdrawalAmountTranslationToNotesException;
import com.modulr.repository.NoteRepository;
import com.modulr.util.NoteHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
@Service
public class NoteServiceImpl implements NoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteServiceImpl.class);

    /**
     * Method transalate the amount to the collection of Notes to be withdrawn
     *
     * @param amount
     * @param noteRepository
     * @return
     * @throws WithdrawalAmountTranslationToNotesException
     */
    @Override
    public Collection<Note> translateWithdrawalAmount(long amount, NoteRepository noteRepository) throws WithdrawalAmountTranslationToNotesException {
        // calculate denominations for "standard"
        final Map<Note, Long> noteMapSmallest = calculateStandard(noteRepository,amount);

        // calculate denominations for "multiples of 5"
        final Map<Note, Long> noteMapMultiplesOf5 = calculateMultiplesOf5(noteRepository, amount);

        Collection<Note> noteList = examineCalculations(noteRepository,amount, noteMapSmallest, noteMapMultiplesOf5);
        if (noteList == null) {
            final WithdrawalAmountTranslationToNotesException error = new WithdrawalAmountTranslationToNotesException(amount);
            LOGGER.error(error.getMessage());
            throw error;
        }
        return noteList;
    }

    /**
     * Translate an amountParameter to note denominations</br>
     *      * using a standard calculation
     *
     * @param noteRepository
     * @param amount
     *            The amountParameter to translate
     * @return  Map<Note, Long>
     * @throws WithdrawalAmountTranslationToNotesException
     */
    private Map<Note, Long> calculateStandard(NoteRepository noteRepository, final long amount) throws WithdrawalAmountTranslationToNotesException {
        final Map<Note, Long> noteMapSmallest = new LinkedHashMap<>();
        long numFIFTY = 0;
        long currentAmount = 0;
        while (noteRepository.getCountOfNotes(Note.FIFTY) > numFIFTY && amount - currentAmount >= Note.FIFTY.getDenomination()) {
            ++numFIFTY;
            currentAmount += Note.FIFTY.getDenomination();
        }
        long numTWENTY = 0;
        while (noteRepository.getCountOfNotes(Note.TWENTY) > numTWENTY && amount - currentAmount >= Note.TWENTY.getDenomination()) {
            ++numTWENTY;
            currentAmount += Note.TWENTY.getDenomination();
        }
        long numTEN = 0;
        while (noteRepository.getCountOfNotes(Note.TEN) > numTEN && amount - currentAmount >= Note.TEN.getDenomination()) {
            ++numTEN;
            currentAmount += Note.TEN.getDenomination();
        }
        long numFIVE = 0;
        while (noteRepository.getCountOfNotes(Note.FIVE) > numFIVE && amount - currentAmount >= Note.FIVE.getDenomination()) {
            ++numFIVE;
            currentAmount += Note.FIVE.getDenomination();
        }
        if (amount - currentAmount > 0) {
            // amountParameter cannot be expressed in supported note
            // denominations
            final WithdrawalAmountTranslationToNotesException error = new WithdrawalAmountTranslationToNotesException(amount);
            LOGGER.error(error.getMessage());
            throw error;
        }
        noteMapSmallest.put(Note.FIFTY, numFIFTY);
        noteMapSmallest.put(Note.TWENTY, numTWENTY);
        noteMapSmallest.put(Note.TEN, numTEN);
        noteMapSmallest.put(Note.FIVE, numFIVE);
        return noteMapSmallest;
    }

    /**
     * Translate an amountParameter to note denominations</br>
     * using a "multiples of 5" calculation
     *
     * @param noteRepository
     * @param amount
     */
    private Map<Note, Long> calculateMultiplesOf5(NoteRepository noteRepository, final long amount) {
        final Map<Note, Long> noteMapMultiplesOf5 = new LinkedHashMap<>();
        long numFIFTY = 0;
        long currentAmount = 0;
        while (amount - currentAmount >= Note.FIFTY.getDenomination() && numFIFTY < 5) {
            ++numFIFTY;
            currentAmount += Note.FIFTY.getDenomination();
        }
        long numTWENTY = 0;
        if (noteRepository.getCountOfNotes(Note.TWENTY) >0 && amount - currentAmount > Note.TWENTY.getDenomination()) {
            while (amount - currentAmount >= Note.TWENTY.getDenomination() && numTWENTY < 5) {
                ++numTWENTY;
                currentAmount += Note.TWENTY.getDenomination();
            }
        }
        long numTEN = 0;
        if (noteRepository.getCountOfNotes(Note.TEN) >0 && amount - currentAmount > Note.TEN.getDenomination()) {
            while (amount - currentAmount >= Note.TEN.getDenomination() && numTEN < 5) {
                ++numTEN;
                currentAmount += Note.TEN.getDenomination();
            }
        }
        long numFIVE = 0;
        if (noteRepository.getCountOfNotes(Note.FIVE) >0 &&  amount - currentAmount >= Note.FIVE.getDenomination()) {
            while (amount - currentAmount >= Note.FIVE.getDenomination() && numTEN < 5) {
                ++numFIVE;
                currentAmount += Note.FIVE.getDenomination();
            }
        }
        noteMapMultiplesOf5.put(Note.FIFTY, numFIFTY);
        noteMapMultiplesOf5.put(Note.TWENTY, numTWENTY);
        noteMapMultiplesOf5.put(Note.TEN, numTEN);
        noteMapMultiplesOf5.put(Note.FIVE, numFIVE);
        return noteMapMultiplesOf5;
    }

    /**
     * Determine which translation is best suitable
     *
     * @param noteRepository
     *            The underlying notes repository
     * @param noteMapSmallest
     *            The notes from standard calculation
     * @param noteMapMultiplesOf5
     *            The notes from "multiples of 5" calculation
     *
     * @return The notes to be delivered
     */
    private Collection<Note> examineCalculations(final NoteRepository noteRepository, final long amount,
                                                 final Map<Note, Long> noteMapSmallest, final Map<Note, Long> noteMapMultiplesOf5) {
        Collection<Note> noteList = null;
        Collection<Note> noteListMultiplesOf5 = NoteHelper.toNoteList(noteMapMultiplesOf5);
        Collection<Note> noteListStandard = NoteHelper.toNoteList(noteMapSmallest);
        if (noteList == null) {
            // examine "multiples of 5" calculation
            try {
                noteRepository.tryRemoveNoteList(noteListMultiplesOf5);
                noteList = noteListMultiplesOf5;
            } catch (ATMException cause) {
                LOGGER.info("Insufficient notes for 'multiples of 5' calculation of amount '{}'", amount);
            }
        }
        if (noteList == null) {
            // examine standard calculation
            try {
                noteRepository.tryRemoveNoteList(noteListStandard);
                noteList = noteListStandard;
            } catch (ATMException cause) {
                LOGGER.info("Insufficient notes for standard calculation of amount '{}'", amount);
            }
        }

        if(noteRepository.getCountOfNotes(Note.FIVE) >0){
            final Collection<Note> noteListMultiplesOf5AtLeastOneFIVE = examineAtLeastOneFIVE(noteRepository, amount, noteListMultiplesOf5);
            if (noteListMultiplesOf5AtLeastOneFIVE == null) {
                final Collection<Note> noteListStandardAtLeastOneFIVE = examineAtLeastOneFIVE( noteRepository,amount,noteListStandard);
                if (noteListStandardAtLeastOneFIVE != null) {
                    noteList = noteListStandardAtLeastOneFIVE;
                }
            } else {
                noteList = noteListMultiplesOf5AtLeastOneFIVE;
            }
        } else {
            noteList = noteListStandard;
        }
        return noteList;
    }

    /**
     * Try to disburse at least one note of denomination FIVE
     *
     * @param noteRepository
     *            The underlying notes repository
     * @param amount
     *            The amount to translate
     * @param noteListToExamine
     *            The original notes translation to be transformed
     * @return The transformed notes</br>
     *         having at least one note of denomination FIVE</br>
     *         (if applicable)
     */
    private Collection<Note> examineAtLeastOneFIVE( final NoteRepository noteRepository,final long amount, Collection<Note> noteListToExamine) {
        if (noteListToExamine == null) {
            return null;
        }
        Collection<Note> noteList = null;
        final Map<Note, Long> noteMapAtLeastOneFIVE = NoteHelper.toNoteMap(noteListToExamine);
        final Long numFIVE = noteMapAtLeastOneFIVE.get(Note.FIVE);
        final Long numTEN = noteMapAtLeastOneFIVE.get(Note.TEN);
        final Long numTWENTY = noteMapAtLeastOneFIVE.get(Note.TWENTY);
        final Long numFIFTY = noteMapAtLeastOneFIVE.get(Note.FIFTY);
        if (numFIVE == null && isSufficientNoteAvailable(noteRepository, Note.FIVE, Long.valueOf(2L))) {
            if (numTEN != null) {
                noteMapAtLeastOneFIVE.put(Note.TEN, Long.valueOf(numTEN.longValue() - 1));
                noteMapAtLeastOneFIVE.put(Note.FIVE, Long.valueOf(2));
            } else if (numTWENTY != null) {
                noteMapAtLeastOneFIVE.put(Note.TWENTY, Long.valueOf(numTWENTY.longValue() - 1));
                boolean tenPresent = isSufficientNoteAvailable(noteRepository, Note.TEN, Long.valueOf(1L));
                if (tenPresent) {
                    noteMapAtLeastOneFIVE.put(Note.TEN, Long.valueOf(1L));
                    noteMapAtLeastOneFIVE.put(Note.FIVE, Long.valueOf(2L));
                } else {
                    noteMapAtLeastOneFIVE.put(Note.FIVE, Long.valueOf(4L));
                }

            } else if (numFIFTY != null) {
                noteMapAtLeastOneFIVE.put(Note.FIFTY, Long.valueOf(numFIFTY.longValue() - 1));
                boolean twentyPresent= isSufficientNoteAvailable(noteRepository, Note.TWENTY, Long.valueOf(2L));
                if(twentyPresent){
                    noteMapAtLeastOneFIVE.put(Note.TWENTY, twentyPresent? Long.valueOf(2L) : 0);
                    noteMapAtLeastOneFIVE.put(Note.FIVE, Long.valueOf(2L));
                } else {
                    twentyPresent = isSufficientNoteAvailable(noteRepository, Note.TWENTY, Long.valueOf(1L));
                    if (twentyPresent) {
                        noteMapAtLeastOneFIVE.put(Note.TWENTY, twentyPresent ? Long.valueOf(1L) : 0);
                        boolean tenPresent = isSufficientNoteAvailable(noteRepository, Note.TEN, Long.valueOf(2L));
                        if (tenPresent) {
                            noteMapAtLeastOneFIVE.put(Note.TEN, Long.valueOf(2L));
                            noteMapAtLeastOneFIVE.put(Note.FIVE, Long.valueOf(2L));
                        } else {
                            noteMapAtLeastOneFIVE.put(Note.TEN, Long.valueOf(1L));
                            noteMapAtLeastOneFIVE.put(Note.FIVE, Long.valueOf(4L));
                        }
                    }
                }
            }
            try {
                final Collection<Note> noteListAtLeastOneFIVE = NoteHelper.toNoteList(noteMapAtLeastOneFIVE);
                noteRepository.tryRemoveNoteList(noteListAtLeastOneFIVE);
                noteList = noteListAtLeastOneFIVE;
            } catch (ATMException cause) {
                LOGGER.info("Insufficient notes for 'at least one FIVE' calculation of amount '{}'", amount);
            }
        }
        return noteList;
    }

    private boolean isSufficientNoteAvailable(NoteRepository noteRepository, Note note, Long count) {
        return noteRepository.getCountOfNotes(note) >=count;
    }
}
