package com.example.iq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next=(Button)findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                launchactivity();
            }
        });
    }
    private void launchactivity(){
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }
}
