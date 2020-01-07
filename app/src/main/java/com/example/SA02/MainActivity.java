package com.example.SA02;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ValuesViewModel mValuesViewModel;
    public static final int NEW_VALUES_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewValuesActivity.class);
                startActivityForResult(intent, NEW_VALUES_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ValuesListAdapter adapter = new ValuesListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mValuesViewModel = ViewModelProviders.of(this).get(ValuesViewModel.class);

        mValuesViewModel.getAllValues().observe(this, new Observer<List<Values>>() {
            @Override
            public void onChanged(@Nullable final List<Values> values) {
                // Update the cached copy of the values in the adapter.
                adapter.setValues(values);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Values myValues = adapter.getValueAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting " + myValues.getDate(), Toast.LENGTH_LONG).show();
                // Delete the value
                mValuesViewModel.deleteValue(myValues);
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Data cleared",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mValuesViewModel.deleteAll();
            return true;
        }
        if (id == R.id.car_stats) {
            Intent intent = new Intent(MainActivity.this, CarStats.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
   }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_VALUES_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Values values = new Values(data.getIntExtra(NewValuesActivity.EXTRA_REPLY,0), data.getDoubleExtra(NewValuesActivity.EXTRA_REPLY1, 0), data.getDoubleExtra(NewValuesActivity.EXTRA_REPLY2, 0), data.getStringExtra(NewValuesActivity.EXTRA_REPLY3));

            mValuesViewModel.insert(values);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
