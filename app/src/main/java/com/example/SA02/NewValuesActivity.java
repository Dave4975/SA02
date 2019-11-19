package com.example.SA02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewValuesActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.SA02.REPLY";
    public static final String EXTRA_REPLY1 = "com.example.android.SA02.REPLY";
    public static final String EXTRA_REPLY2 = "com.example.android.SA02.REPLY";

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
                    String value = mEditMileageView.getText().toString();
                    String value1 = mEditCostView.getText().toString();
                    String value2 = mEditAmountView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, value);
                    replyIntent.putExtra(EXTRA_REPLY, value1);
                    replyIntent.putExtra(EXTRA_REPLY, value2);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
