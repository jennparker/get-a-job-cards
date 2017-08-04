package com.booisajerk.getajobcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jenniferparker on 7/30/17.
 */

public class CardReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "QuizCard.db";
    public static final String LOG_TAG = CardReaderDbHelper.class.getSimpleName();


    // Define a projection that specifies which columns from the database
// you will actually use after this query.
    String[] projection = {

            //TODO add id to contract, I think
            CardReaderContract.CardEntry.COLUMN_NAME_ID,
            CardReaderContract.CardEntry.COLUMN_NAME_QUESTION,
            CardReaderContract.CardEntry.COLUMN_NAME_ANSWER,
            CardReaderContract.CardEntry.COLUMN_NAME_CATEGORY,
            CardReaderContract.CardEntry.COLUMN_NAME_MORE
    };

    public CardReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CardReaderContract.SQL_CREATE_CARD_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "onUpgrade called");
        db.execSQL(CardReaderContract.SQL_DELETE_CARD_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Adding new card_activity to db
    public void addCard(Card card) {
        Log.d(LOG_TAG, "addCard called");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CardReaderContract.CardEntry.COLUMN_NAME_QUESTION, card.getQuestion());
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_ANSWER, card.getAnswer());
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_MORE, card.getMore());
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_CATEGORY, card.getCategory());

        // Inserting Row
        db.insert(CardReaderContract.CardEntry.TABLE_NAME, null, values);
        Log.d(LOG_TAG, "addCard - inserting values into table: " + CardReaderContract.CardEntry.TABLE_NAME);

        db.close();
    }

    public void addCard(String question, String answer, String category, String more) {
        Log.d(LOG_TAG, "addCard called");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_QUESTION, question);
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_ANSWER, answer);
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_MORE, more);
        values.put(CardReaderContract.CardEntry.COLUMN_NAME_CATEGORY, category);

        // Inserting Row
        db.insert(CardReaderContract.CardEntry.TABLE_NAME, null, values);
        Log.d(LOG_TAG, "addCard - inserting values into table: " + CardReaderContract.CardEntry.TABLE_NAME);

        db.close();
    }


    // Get a single card_activity
    public Card getCard(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = CardReaderContract.CardEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                CardReaderContract.CardEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                CardReaderContract.DB_SORT_QUESTION_DESC                                // The sort order
        );

        if (cursor != null)
            cursor.moveToFirst();

        Card card = new Card(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        cursor.close();

        return card;
    }

    // Get all cards
    public List<Card> getAllCards() {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Card> cardList = new ArrayList<Card>();

        String selectQuery = "SELECT  * FROM " + CardReaderContract.CardEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Card card = new Card(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));

                // Adding cards to list
                cardList.add(card);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return cardList;
    }

    // Getting card_activity count
    public int getCardCount() {
        String countQuery = "SELECT  * FROM " + CardReaderContract.CardEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    // Updating single card_activity
    public void updateCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        //contentValues.put(CardReaderContract.CardEntry.COLUMN_NAME_ID, card.getId());
        contentValues.put(CardReaderContract.CardEntry.COLUMN_NAME_QUESTION, card.getQuestion());
        contentValues.put(CardReaderContract.CardEntry.COLUMN_NAME_ANSWER, card.getAnswer());
        contentValues.put(CardReaderContract.CardEntry.COLUMN_NAME_MORE, card.getMore());
        contentValues.put(CardReaderContract.CardEntry.COLUMN_NAME_CATEGORY, card.getCategory());

        db.update(CardReaderContract.CardEntry.TABLE_NAME, contentValues, CardReaderContract.CardEntry.COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(card.getId())});

        Log.d(LOG_TAG, "ID: " + card.getId());

    }

    // Deleting single card_activity
    public void deleteCard(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(CardReaderContract.CardEntry.TABLE_NAME, CardReaderContract.CardEntry._ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public void alterTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String changeTableNameQuery = "ALTER TABLE" + CardReaderContract.CardEntry.TABLE_NAME
                + "RENAME TO" + CardReaderContract.OLD_TABLE_NAME;

        //TOOD not sure on this row
        db.rawQuery(changeTableNameQuery, null);
    }


    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String dropTable = "DROP TABLE " +CardReaderContract.CardEntry.TABLE_NAME;

        Log.d(LOG_TAG, "Table dropped");

        db.rawQuery(dropTable, null);
    }

}
