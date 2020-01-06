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
        final Calculator calc = new Calculator();

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

                    final String getGallons = String.format("%.2f", calc.LitresToGallons(myValues.getAmount()));
                    final String getLitres = String.valueOf(myValues.getAmount());
                    final String getMileage = String.valueOf(myValues.getMileage());
                    final String getDistanceKM = String.format("%.2f", calc.MilesToKilometres(myValues.getMileage()));
                    final String getCPM = String.format("%.2f", calc.CPM(myValues.getCost(), myValues.getMileage()));
                    final String getCPK = String.format("%.2f", calc.CPK(myValues.getCost(), myValues.getMileage()));
                    final String getCPG = String.format("%.2f", calc.CPG(myValues.getCost(), myValues.getAmount()));
                    final String getCPL = String.format("%.2f", calc.CPL(myValues.getCost(), myValues.getAmount()));
                    final String getMPG = String.format("%.1f", calc.MPG(myValues.getMileage(), myValues.getAmount()));
                    final String getMPL = String.format("%.1f", calc.MPL(myValues.getMileage(), myValues.getAmount()));
                    final String getKPG = String.format("%.1f", calc.KPG(myValues.getMileage(), myValues.getAmount()));
                    final String getKPL = String.format("%.1f", calc.KPL(myValues.getMileage(), myValues.getAmount()));
                    setContentView(R.layout.activity_show_data);

                    TextView mGallons = (TextView) findViewById(R.id.fuel_gallons);
                    mGallons.setText(getGallons);
                    TextView mLitres = (TextView) findViewById(R.id.fuel_litres);
                    mLitres.setText(getLitres);
                    TextView mMileage = (TextView) findViewById(R.id.distance_miles);
                    mMileage.setText(getMileage);
                    TextView mKm = (TextView) findViewById(R.id.distance_km);
                    mKm.setText(getDistanceKM);
                    TextView mCPM = (TextView) findViewById(R.id.cpm);
                    mCPM.setText(getCPM);
                    TextView mCPK = (TextView) findViewById(R.id.cpk);
                    mCPK.setText(getCPK);
                    TextView mCPG = (TextView) findViewById(R.id.cpg);
                    mCPG.setText(getCPG);
                    TextView mCPL = (TextView) findViewById(R.id.cpl);
                    mCPL.setText(getCPL);
                    TextView mMPG = (TextView) findViewById(R.id.mpg);
                    mMPG.setText(getMPG);
                    TextView mMPL = (TextView) findViewById(R.id.mpl);
                    mMPL.setText(getMPL);
                    TextView mKPG = (TextView) findViewById(R.id.kpg);
                    mKPG.setText(getKPG);
                    TextView mKPL = (TextView) findViewById(R.id.kpl);
                    mKPL.setText(getKPL);
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
