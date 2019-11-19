package com.example.SA02;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ValuesViewModel extends AndroidViewModel {
    private ValuesRepository mRepository;

    private LiveData<List<Values>> mAllValues;

    public ValuesViewModel(Application application) {
        super(application);
        mRepository = new ValuesRepository(application);
        mAllValues = mRepository.getAllValues();
    }

    LiveData<List<Values>> getAllValues() {
        return mAllValues;
    }

    public void insert(Values values) {
        mRepository.insert(values);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteValue(Values values) {mRepository.deleteValue(values);}
}
