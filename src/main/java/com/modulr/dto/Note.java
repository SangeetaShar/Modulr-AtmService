package com.modulr.dto;

import java.util.Currency;

/**
 * Created by Sangeeta Sharma on 17/08/2018.
 */
public enum Note {

    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50);

    /**
     * The currency, assuming GBP
     */
    public static final Currency CURRENCY = Currency.getInstance("GBP");

    /**
     * The value of the note
     */
    private int denomination;

    Note(final int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }
}
