package com.modulr.exception;

import com.modulr.dto.Note;

import java.text.MessageFormat;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public class ATMException extends RuntimeException {

    public ATMException(final Note note) {
        super(MessageFormat.format("Not enough notes of value '{0}' in ATM",note.getDenomination()));
    }

}
