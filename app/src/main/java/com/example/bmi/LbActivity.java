package com.example.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LbActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lb);
        TextView txt1 = findViewById(R.id.txtv1);
        EditText entertxt = findViewById(R.id.txt1);
        Button checkk = findViewById(R.id.b1);
        Button resett= findViewById(R.id.resset);
        Button goback= findViewById(R.id.go);

        checkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lb = entertxt.getText().toString();
                if (lb.equals("")) {
                    entertxt.setError("Please Enter in feet");
                    entertxt.requestFocus();
                    return;
                }
                float weilb = Float.parseFloat(lb);
                float result = (float) (weilb * 0.4536);
                String resul= String.format("%.2f",result);
                txt1.setText(resul+" kg");
            }
        });
        resett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                entertxt.setText("");

            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt=new Intent(LbActivity.this,SplashActivity.class);
                startActivity(intentt);
            }
        });
    }
}