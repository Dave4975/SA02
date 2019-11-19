package com.example.SA02;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Values.class}, version = 1, exportSchema = false)
public abstract class ValuesRoomDatabase extends RoomDatabase {
    public abstract ValuesDAO valueDao();

    private static ValuesRoomDatabase INSTANCE;

    public static ValuesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ValuesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ValuesRoomDatabase.class, "value_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ValuesDAO mDao;
        String[] words = {"dolphin", "cobra"};

        PopulateDbAsync(ValuesRoomDatabase db) {
            mDao = db.valueDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            // If we have no words, then create the initial list of words
            if (mDao.getAnyValue().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Values values = new Values(words[i]);
                    mDao.insert(values);
                }
            }
            return null;
        }
    }
}
