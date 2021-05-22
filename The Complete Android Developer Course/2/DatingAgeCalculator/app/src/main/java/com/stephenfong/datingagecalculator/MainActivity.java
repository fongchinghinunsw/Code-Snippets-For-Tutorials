package com.stephenfong.datingagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtAge;
    Button btnCalculate;
    TextView tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtAge = findViewById(R.id.edtAge);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvAge = findViewById(R.id.tvAge);
    }

    public void calculateAge(View view) {
        String ageInString = edtAge.getText().toString();
        if (!ageInString.equals("")) {
            int age = Integer.parseInt(ageInString);
            double dateAge = (age + 10) * 0.5;
            tvAge.setText(dateAge + "");
        }
    }
}