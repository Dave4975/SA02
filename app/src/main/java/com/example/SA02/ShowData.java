package com.example.SA02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static com.example.SA02.ValuesListAdapter.EXTRA_MESSAGE_KEY;

public class ShowData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        int currentId;
        final ValuesListAdapter adapter = new ValuesListAdapter(this);

        try {
            currentId = getIntent().getIntExtra(EXTRA_MESSAGE_KEY, 0);
            //Log.d("Nick", String.valueOf(currentId));
            Values myValues = adapter.getValueAtPosition(currentId);
        }
        catch(Exception e) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
