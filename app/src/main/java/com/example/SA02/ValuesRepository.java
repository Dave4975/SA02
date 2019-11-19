package com.example.SA02;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ValuesRepository {
    private ValuesDAO mValuesDao;
    private LiveData<List<Values>> mAllValues;

    ValuesRepository(Application application) {
        ValuesRoomDatabase db = ValuesRoomDatabase.getDatabase(application);
        mValuesDao = db.valueDao();
        mAllValues = mValuesDao.getAllValues();
    }

    LiveData<List<Values>> getAllValues() {
        return mAllValues;
    }

    public void insert (Values values) {
        new insertAsyncTask(mValuesDao).execute(values);
    }
    public void deleteAll()  { new deleteAllValuesAsyncTask(mValuesDao).execute(); }
    public void deleteValue(Values values)  { new deleteValueAsyncTask(mValuesDao).execute(values); }

    /////////////////////////////
    // DB operation inner classes
    /////////////////////////////

    private static class insertAsyncTask extends AsyncTask<Values, Void, Void> {

        private ValuesDAO mAsyncTaskDao;

        insertAsyncTask(ValuesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Values... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllValuesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ValuesDAO mAsyncTaskDao;

        deleteAllValuesAsyncTask(ValuesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteValueAsyncTask extends AsyncTask<Values, Void, Void> {
        private ValuesDAO mAsyncTaskDao;

        deleteValueAsyncTask(ValuesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Values... params) {
            mAsyncTaskDao.deleteValue(params[0]);
            return null;
        }
    }
}


