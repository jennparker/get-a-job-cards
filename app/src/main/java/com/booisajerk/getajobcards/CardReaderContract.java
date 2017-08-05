package com.booisajerk.getajobcards;

import android.provider.BaseColumns;

/**
 * Created by jenniferparker on 7/30/17.
 */

public class CardReaderContract {

    //Private so no one accidentally instantiates it.
    private CardReaderContract() {
    }

    /* Inner class that defines the table contents
     * Implement BaseColumns so _ID field is included. This helps for working with CursorAdapter */
    public static class CardEntry implements BaseColumns {
        public static final String TABLE_NAME = "cards";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_ANSWER = "answer";
        public static final String COLUMN_NAME_MORE = "more";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }

    public static final String OLD_TABLE_NAME = "old_card";
    public static final String DB_SORT_QUESTION_DESC = CardReaderContract.CardEntry.COLUMN_NAME_QUESTION + " DESC";

    public static final String SQL_CREATE_CARD_ENTRIES =
            "CREATE TABLE " + CardReaderContract.CardEntry.TABLE_NAME + " (" +
                    CardReaderContract.CardEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    CardReaderContract.CardEntry.COLUMN_NAME_QUESTION + " TEXT," +
                    CardReaderContract.CardEntry.COLUMN_NAME_ANSWER + " TEXT," +
                    CardReaderContract.CardEntry.COLUMN_NAME_MORE + " TEXT," +
                    CardReaderContract.CardEntry.COLUMN_NAME_CATEGORY + " TEXT)";

    public static final String SQL_DELETE_CARD_ENTRIES =
            "DROP TABLE IF EXISTS " + CardReaderContract.CardEntry.TABLE_NAME;
}


