package com.example.SA02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Date;

public class NewValuesActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "mMileage";
    public static final String EXTRA_REPLY1 = "mCost";
    public static final String EXTRA_REPLY2 = "mAmount";
    public static final String EXTRA_REPLY3 = "mDate";

    private EditText mEditMileageView;
    private EditText mEditCostView;
    private EditText mEditAmountView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_values);
        mEditMileageView = findViewById(R.id.edit_mileage);
        mEditCostView = findViewById(R.id.edit_cost);
        mEditAmountView = findViewById(R.id.edit_amount);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditMileageView.getText()) || TextUtils.isEmpty(mEditCostView.getText()) || TextUtils.isEmpty(mEditAmountView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    int value = Integer.valueOf(String.valueOf(mEditMileageView.getText()));
                    double value1 = Double.valueOf(String.valueOf(mEditCostView.getText()));
                    double value2 = Double.valueOf(String.valueOf(mEditAmountView.getText()));
                    String currentTime = String.valueOf(Calendar.getInstance().getTime());

                    replyIntent.putExtra(EXTRA_REPLY, value);
                    replyIntent.putExtra(EXTRA_REPLY1, value1);
                    replyIntent.putExtra(EXTRA_REPLY2, value2);
                    replyIntent.putExtra(EXTRA_REPLY3, currentTime);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
