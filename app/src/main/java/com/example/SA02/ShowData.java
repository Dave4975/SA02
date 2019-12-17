package com.example.SA02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.SA02.ValuesListAdapter.EXTRA_MESSAGE_KEY;

public class ShowData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        final ValuesListAdapter adapter = new ValuesListAdapter(this);
        ValuesViewModel mValuesViewModel;

        mValuesViewModel = ViewModelProviders.of(this).get(ValuesViewModel.class);

        mValuesViewModel.getAllValues().observe(this, new Observer<List<Values>>() {

            @Override
            public void onChanged(@Nullable final List<Values> values) {
                // Update the cached copy of the values in the adapter.
                adapter.setValues(values);
                final int currentId;

                try {
                    currentId = getIntent().getIntExtra(EXTRA_MESSAGE_KEY, 0);
                    //Log.d("Nick", String.valueOf(currentId)
                    Values myValues = adapter.getValueAtPosition(currentId);

                    adapter.getItemCount();

                    final String edittext = String.valueOf(myValues.getAmount());
                    setContentView(R.layout.activity_show_data);
                    TextView mText = (TextView) findViewById(R.id.fuel_litres);
                    mText.setText(edittext);
                }
                catch(Exception e) {
                    Log.d("Nick", String.valueOf(e));
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.not_found,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
