package com.example.richardsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

enum BMIGroup
{
    UNDER_WEIGHT, HEALTHY_WEIGHT, OVER_WEIGHT, OBESE;
}


public class BMIAnalyzerActivity extends AppCompatActivity {
    public BMIGroup m_BMIGroup;
    public static final double  BMI_Upper_Limit = 25.0;
    public static final double  BMI_Lower_Limit = 18.5;
    public static final double  BMI_Obese_Limit = 30.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i_analyzer);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.BMI_Analyzer_textView1);
        textView.setText("Your BMI value is: " + message);

        textView = findViewById(R.id.textView_BMI_analyzer_gainorloss_tag);
        textView.setText("To reach healthy BMI\nYou need to: ");

        double bmi_val = MainActivity.gBMI_result;
        FindBMIRange(bmi_val);
        CalcHealthyBMIDelta(bmi_val);
    }

    /* This function classifies person's weight group
       < 18.5 -- Underweight
       18.5 - 24.9 -- Healthy weight
       25.0 - 29.9 Overweight
       > 30.0 -- Obese
     */
    private void FindBMIRange(double inputBMI)
    {
        TextView text_output = findViewById(R.id.textView_BMIGroup_result);
        if (inputBMI < BMI_Lower_Limit)
        {
            m_BMIGroup = BMIGroup.UNDER_WEIGHT;
            text_output.setTextColor(Color.BLUE);
            text_output.setText("Eat more!\n You are under weight :)");
        }
        else if (inputBMI < BMI_Upper_Limit && inputBMI >= BMI_Lower_Limit)
        {
            m_BMIGroup = BMIGroup.HEALTHY_WEIGHT;
            text_output.setTextColor(Color.GREEN);
            text_output.setText("Congratulation,\n you are healthy weight :)");
        }
        else if (inputBMI >= BMI_Upper_Limit && inputBMI < BMI_Obese_Limit)
        {
            m_BMIGroup = BMIGroup.OVER_WEIGHT;
            text_output.setTextColor(Color.MAGENTA);
            text_output.setText("You are over-weight :(");
        } else
        {
            m_BMIGroup = BMIGroup.OBESE;
            text_output.setTextColor(Color.RED);
            text_output.setText("You are Obese :( :(");
        }
    }

    /* This function finds out how much weight person needs to gain/loss to reach healthy range */
    private void CalcHealthyBMIDelta(double inputBMI)
    {
        double delta_weight = 0.0f;
        double ideal_weight = 0.0f;
        boolean need_gain_weight = false;

        /* First see how far person's weight is away from healthy range */
        if (inputBMI >= BMI_Upper_Limit)
        {
            ideal_weight = BMI_Upper_Limit * Math.pow(MainActivity.gInput_height, 2);
            delta_weight = Math.abs(ideal_weight - MainActivity.gInput_weight);
            need_gain_weight = false;
        } else if (inputBMI < BMI_Lower_Limit)
        {
            ideal_weight = BMI_Lower_Limit * Math.pow(MainActivity.gInput_height, 2);
            delta_weight = Math.abs(ideal_weight - MainActivity.gInput_weight);
            need_gain_weight = true;
        } else
        {
            delta_weight = 0.0f;
        }

        if (delta_weight != 0)
        {
            /* delta_bmi * input_weight gives you how much weight to gain/loss to reach nearest limit */
            TextView textView = findViewById(R.id.textView_BMI_analyzer_gainorloss_val);
            textView.setText(String.format("%.2f", delta_weight) + "kg\nto reach\n" +
                             String.format("%.2f", ideal_weight) + "kg");

            textView = findViewById(R.id.textView_BMI_analyzer_gainorloss);
            if (need_gain_weight == true)
            {
                textView.setTextColor(Color.GREEN);
                textView.setText("Gain");
            } else
            {
                textView.setTextColor(Color.RED);
                textView.setText("Loss");
            }
        }
    }
}