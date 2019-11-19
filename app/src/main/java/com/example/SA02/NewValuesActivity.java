package com.example.SA02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewValuesActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.SA02.REPLY";

    private EditText mEditValueView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_values);
        mEditValueView = findViewById(R.id.edit_word);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditValueView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String value = mEditValueView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, value);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
