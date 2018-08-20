package com.modulr.service;

import com.modulr.dto.Note;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public enum TranslateWithdrawalAmountTestData {
    Translate20asAtLeastOneFIVE(20L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TEN, 1L);
            noteMap.put(Note.FIVE, 2L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TEN, 1L);
            noteMapTranslation.put(Note.FIVE, 2L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate20asAtLeastOneFIVEWhen2TenAvailable(20L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TEN, 2L);
            noteMap.put(Note.FIVE, 2L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TEN, 1L);
            noteMapTranslation.put(Note.FIVE, 2L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate20asTwoTEN(20L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TEN, 2L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TEN, 2L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate20asOneTWENTY(20L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TWENTY, 1L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TWENTY, 1L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate20asOneTWENTYEvenIfOne5NoteIsAvailable(20L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TWENTY, 1L);
            noteMap.put(Note.FIVE, 1L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TWENTY, 1L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate20asFour5(20L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TWENTY, 1L);
            noteMap.put(Note.FIVE, 5L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.FIVE, 4L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate25asTwo10AndOne5(25L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TEN, 3L);
            noteMap.put(Note.FIVE, 4L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TEN, 2L);
            noteMapTranslation.put(Note.FIVE, 1L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate50(50L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.TEN, 2L);
            noteMap.put(Note.TWENTY, 2L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TWENTY, 2L);
            noteMapTranslation.put(Note.TEN, 1L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate50asAtLeastOneFIVE(50L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.FIVE, 4L);
            noteMap.put(Note.FIFTY, 1L);
            noteMap.put(Note.TWENTY, 2L);
            noteMap.put(Note.TEN, 1L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TWENTY, 2L);
            noteMapTranslation.put(Note.FIVE, 2L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate50asAtLeastOneFIVEAndTwenty(50L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.FIVE, 4L);
            noteMap.put(Note.FIFTY, 1L);
            noteMap.put(Note.TWENTY, 1L);
            noteMap.put(Note.TEN, 1L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TWENTY, 1L);
            noteMapTranslation.put(Note.TEN, 1L);
            noteMapTranslation.put(Note.FIVE, 4L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    },
    Translate50asAtLeastOneFIVEAndTwo10(50L) {

        @Override
        public Map<Note, Long> getNoteMap() {
            final Map<Note, Long> noteMap = new LinkedHashMap<>();
            noteMap.put(Note.FIVE, 4L);
            noteMap.put(Note.FIFTY, 1L);
            noteMap.put(Note.TWENTY, 1L);
            noteMap.put(Note.TEN, 2L);
            return noteMap;
        }

        @Override
        public Map<Note, Long> getNoteMapTranslation() {
            final Map<Note, Long> noteMapTranslation = new LinkedHashMap<>();
            noteMapTranslation.put(Note.TWENTY, 1L);
            noteMapTranslation.put(Note.TEN, 2L);
            noteMapTranslation.put(Note.FIVE, 2L);
            return noteMapTranslation;
        }

        @Override
        public boolean isErrorExpected() {
            return false;
        }
    }
    ;

    private final long amount;

    TranslateWithdrawalAmountTestData(final long amount) {
        this.amount = amount;
    }

    public final long getAmount() {
        return amount;
    }

    public abstract Map<Note, Long> getNoteMap();

    public abstract Map<Note, Long> getNoteMapTranslation();

    public abstract boolean isErrorExpected();

    public final Object[] getParameters() {
        return //
                new Object[] { //
                        name(), //
                        getNoteMap(), //
                        getAmount(), //
                        getNoteMapTranslation(), //
                        isErrorExpected() //
                };
    }
}
