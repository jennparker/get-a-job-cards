package com.booisajerk.getajobcards;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

/**
 * Created by jenniferparker on 9/19/17.
 */

@Database(entities = {Card.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = "Parker " + AppDatabase.class.getSimpleName();

    private static AppDatabase INSTANCE;

    public abstract CardDAO cardModel();


    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            Log.d(LOG_TAG, "Creating new inMemoryDatabase instance");
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            .allowMainThreadQueries()
                            .addCallback(rdc)
                            .build();
        }
        return INSTANCE;
    }

    //TODO Use this when ready to create a persistent db and move to a background thread
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            Log.d(LOG_TAG, "Creating new database instance");
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "card-data")
                            .addCallback(rdc)
                            .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {
            Log.d(LOG_TAG, "SQL Database created. Path is: " + db.getPath()
                    + " and version is " + db.getVersion());
        }

        public void onOpen(SupportSQLiteDatabase db) {
            Log.d(LOG_TAG, "SQL Database opened.");
        }
    };

    public static void destroyInstance() {
        INSTANCE = null;
        Log.d(LOG_TAG, "AppDatabase instance destroyed");
    }
}