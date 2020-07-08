package com.example.richardsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static double gBMI_result = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Calculate button */
    public void CalculateBMI(View view)
    {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editTextHeight = (EditText) findViewById(R.id.editTextNumberDecimalHeight);
        EditText editTextWeight = (EditText) findViewById(R.id.editTextNumberDecimalWeight);
        EditText editTextSex    = (EditText) findViewById(R.id.editTextNumberDecimalSex);

        /* BMI formula is kg / height^2(meter) */
        double BMI_result = Double.parseDouble(editTextWeight.getText().toString()) /
                            DivideBy100(Double.parseDouble(editTextHeight.getText().toString())) /
                            DivideBy100(Double.parseDouble(editTextHeight.getText().toString()));
        gBMI_result = BMI_result;
        /* round to 2 decimal places */
        String message = "Height = " + editTextHeight.getText().toString() +
                         "\nWeight = " + editTextWeight.getText().toString() +
                         "\n\n BMI = " + String.format("%.2f", gBMI_result);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private double DivideBy100(double input)
    {
        return input / 100.0;
    }
}