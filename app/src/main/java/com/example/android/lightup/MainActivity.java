package com.example.android.lightup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button button;
    Button off;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Parameters params;
    TextView textview;
    String time;
    EditText edittext;
    CountDownTimer countdowntimer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        off= (Button) findViewById(R.id.off);
        button = (Button) findViewById(R.id.submitbutton);
        textview=(TextView)findViewById(R.id.enter);
        edittext=(EditText)findViewById(R.id.edittext);
        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        Intent abc= new Intent(this,ServiceChatHead.class);
        startService(abc);

        if(!hasFlash) {

            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            alert.show();
            return;
        }

        getCamera();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    time= edittext.getText().toString();
                    textview.setText("Time Left: " + time);
                    int k=Integer.parseInt(time);
                    countdowntimer = new CountDownTimerClass(k*1000,1000);
                    countdowntimer.start();
                    turnOnFlash();
                    button.setText("OFF");



            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                turnOffFlash();
                button.setText("SUBMIT");
                textview.setText("STOPPED");
                countdowntimer = new CountDownTimerClass(0,1000);

            }
        });
    }

    private void getCamera() {

        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            }catch (Exception e) {

            }
        }

    }

    private void turnOnFlash() {

        if(!isFlashOn) {
            if(camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    private void turnOffFlash() {

        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

   /* @Override
    protected void onPause() {
        super.onPause();


        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(hasFlash)
            turnOffFlash();
    }*/

    @Override
    protected void onStart() {
        super.onStart();


        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public class CountDownTimerClass extends CountDownTimer {

        public CountDownTimerClass(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {

            textview.setText(" Count Down Finish ");

            turnOffFlash();
            button.setText("SUBMIT");
        }
        @Override
        public void onTick(long millisUntilFinished) {

            long progress = (millisUntilFinished/1000);

            textview.setText(progress+"");

            if(progress==0){

                onFinish();
            }

        }



        }
    }


