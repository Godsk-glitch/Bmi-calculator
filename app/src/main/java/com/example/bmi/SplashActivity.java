
package com.example.bmi;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.SimpleDateFormat;
        import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    float bmiValue;
    Spinner Gender;

    private long backPressedTime;
    private Toast backToast;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EditText edWeg, edHei,uname,phone;
        TextView txtRes, textInter;
        Button btnRes, btnReset, feetid, lbid, rptbtn,search;


        Gender=(Spinner) findViewById(R.id.spinner2);
        edWeg = (EditText) findViewById(R.id.edweg);
        edHei = (EditText) findViewById(R.id.edhei);
        textInter = (TextView) findViewById(R.id.txtinter);
        txtRes = (TextView) findViewById(R.id.txtres);
        btnRes = (Button) findViewById(R.id.btnres);
        btnReset = (Button) findViewById(R.id.btnreset);
        feetid = (Button) findViewById(R.id.feetid);
        lbid = (Button) findViewById(R.id.lbid);
        rptbtn = (Button) findViewById(R.id.faction);
        uname=findViewById(R.id.uname);
        search=findViewById(R.id.search);
        phone=findViewById(R.id.phone);
        db=new DBHelper(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText().toString().length()==0){
                    Toast.makeText(SplashActivity.this, "phone number  cant be Empty", Toast.LENGTH_SHORT).show();
                }else{
                    String phoneTXT=phone.getText().toString();
                    Cursor res = db.getData(phoneTXT);
                    if(res.getCount()==0){
                        Toast.makeText(SplashActivity.this,"No Entry Exists",Toast.LENGTH_LONG).show();
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append("Name :"+res.getString(0)+"\n");
                        buffer.append("Height :"+res.getString(1)+"\n");
                        buffer.append("Weight :"+res.getString(2)+"\n");
                        buffer.append("Status :"+res.getString(3)+"\n");
                        buffer.append("Value :"+res.getString(4)+"\n");
                        buffer.append("Date :"+res.getString(5)+"\n\n");
                    }
                    AlertDialog.Builder builder= new AlertDialog.Builder(SplashActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Your Previous Records");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }

            }
        });
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weg = edWeg.getText().toString();
                String hei = edHei.getText().toString();

                if(weg.equals("0") || weg.equals("00"))
                {
                    edWeg.setError("Weight Can't be zero");
                    edWeg.requestFocus();
                    return;
                }
                if(hei.equals("0") || hei.equals("00"))
                {
                    edWeg.setError("Height Can't be zero");
                    edWeg.requestFocus();
                    return;
                }
                if (weg.equals("")) {
                    edWeg.setError("Please Enter Weight");
                    edWeg.requestFocus();
                    return;
                }
                if (hei.equals("")) {
                    edHei.setError("Please Enter Height");
                    edHei.requestFocus();
                    return;
                }

                float weight = Float.parseFloat(weg);
                float height = Float.parseFloat(hei) / 100;
                String m=Gender.getSelectedItem().toString();
                if(m.equals("MALE"))
                {
                    bmiValue = BMICalculate(weight, height);
                }
                else if(m.equals("FEMALE"))
                {
                    bmiValue = BMICalculate2(weight, height);
                }
                else
                {
                    Toast.makeText(SplashActivity.this, "Please select one gender", Toast.LENGTH_SHORT).show();
                }


                String bmi = String.format("%.2f", bmiValue);
                textInter.setText(interpreteBMI(bmiValue));
                txtRes.setText("BMI= " + bmi);
                String nameTXT=uname.getText().toString();
                String heightTXT=edHei.getText().toString();
                String weightTXT=edWeg.getText().toString();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                String bmiInter=textInter.getText().toString();
                String bmiRes=txtRes.getText().toString();
                String dateTXT=simpleDateFormat.format(new Date());
                String phoneTXT=phone.getText().toString();
                Boolean check =db.addData(nameTXT,heightTXT,weightTXT,bmiInter,bmiRes,dateTXT,phoneTXT);
                if(check){
                    Toast.makeText(SplashActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SplashActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        feetid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feetintent = new Intent(SplashActivity.this, FeetActivity.class);
                startActivity(feetintent);
            }
        });
        lbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feetintent = new Intent(SplashActivity.this, LbActivity.class);
                startActivity(feetintent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edWeg.setText("");
                edHei.setText("");
                textInter.setText("");
                txtRes.setText("");
            }
        });

        rptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bmiValue<16)
                {
                    Intent int1= new Intent(SplashActivity.this,Exampleint.class);
                    startActivity(int1);
                }
                else if(bmiValue<18.5)
                {
                    Intent int1= new Intent(SplashActivity.this,Undw.class);
                    startActivity(int1);
                }
                else if(bmiValue<25)
                {
                    Intent int1= new Intent(SplashActivity.this,Norm.class);
                    startActivity(int1);
                }
                else if(bmiValue<30)
                {
                    Intent int1= new Intent(SplashActivity.this,Ovw.class);
                    startActivity(int1);
                }
                else
                {
                    Intent int1= new Intent(SplashActivity.this,Obese.class);
                    startActivity(int1);

                }

            }
        });



    }
        public float BMICalculate ( float weight, float height)
        {
            return weight / (height * height);
        }
        public float BMICalculate2 ( float weight, float height)
        {
            return (float) ((weight / (height * height)) - 0.2);
        }

        public String interpreteBMI ( float bmiValue)
        {
            if (bmiValue < 16) {
                return "Severly UnderWeight";

            } else if (bmiValue < 18.5) {
                return "UnderWeight";
            } else if (bmiValue < 25) {
                return "Normal";
            } else if (bmiValue < 30) {
                return "OverWeight";
            } else
                return "Obese";
        }
        public void onBackPressed ()
        {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }

            backPressedTime = System.currentTimeMillis();
        }
    }
