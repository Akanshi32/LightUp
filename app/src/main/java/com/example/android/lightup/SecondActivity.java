    package com.example.android.lightup;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView seconds;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        seconds=(TextView)findViewById(R.id.sec);
        b=(Button)findViewById(R.id.offbutton) ;
        seconds.setText("Switching Off in "+getIntent().getStringExtra("time")+" seconds");
        View.OnClickListener subs = new View.OnClickListener() {
            @Override
            public void onClick(View view) {







            }
        };
        b.setOnClickListener(subs);
    }
}
