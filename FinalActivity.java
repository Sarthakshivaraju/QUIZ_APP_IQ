package com.example.iq;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FinalActivity extends AppCompatActivity {
    TextView tv,tv2,tv3;
    EditText number;
    private SQLiteDatabase db;
    private Cursor c;
    Button btn,btn1,btn2,btn3;
    int s;String n,query;
    private static final String x="SELECT * FROM scores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        number=(EditText)findViewById(R.id.editText);
        tv=(TextView)findViewById(R.id.textView3);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.tv3);
        btn=(Button)findViewById(R.id.button1);
        btn1=(Button)findViewById(R.id.btn4);
        btn2=(Button)findViewById(R.id.button6);
        btn3=(Button)findViewById(R.id.sms);
        Bundle data=getIntent().getExtras();
        s=data.getInt("score");
        n=data.getString("Name");
        tv.setText("Score: "+s);
        if(s>50)
            tv3.setText("YOU HAVE A HIGH IQ");
        else if(s>20)
            tv3.setText("YOUR IQ IS AVERAGE");
        else {
            tv3.setText("YOU HAVE A POOR IQ");

        }
        createdatabase();
        c=db.rawQuery(x, null);
        if(!c.moveToFirst())
        {
            query="INSERT INTO scores VALUES('"+n+"','"+s+"')";
            db.execSQL(query);
            tv2.setText("You got a high score!");
        }
        else
        {
            c.moveToLast();
            if(s>Integer.parseInt(c.getString(1)))
            {
                query="UPDATE scores SET name='"+n+"', score='"+s+"' where score='"+c.getString(1)+"'";
                db.execSQL(query);
                tv2.setText("You got a high score!");
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i=new Intent(getApplicationContext(), HomeScreen.class);
                i.putExtra("Name", n);
                startActivity(i);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),graph.class);
                intent.putExtra("Score",s);
                startActivity(intent);

            }


        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),gmail.class);
                startActivity(intent);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 {
                    String no=number.getText().toString();
                    try{
                        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(no, null,"IQ SCORE "+ Integer.toString(s), pi, null);
                        Toast.makeText(getApplicationContext(), "Message sent sucessfully!", Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Enter correct phone number", Toast.LENGTH_LONG).show();
                    }
                }




            }
        });
    }

    protected void createdatabase()
    {
        db=openOrCreateDatabase("ScoresDB.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS scores(name VARCHAR, score NUMBER)");
    }
    }

