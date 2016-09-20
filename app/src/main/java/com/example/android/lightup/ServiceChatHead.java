package com.example.android.lightup;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
import android.widget.ImageView;

 * Created by Akanshi on 9/18/2016.
 */
public class ServiceChatHead extends AppCompatActivity{
    private WindowManager windowManager;
    private ImageView chatImage;

    public IBinder onBind(Intent intent) {


        return null;
    }

     public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         setContentView(R.layout.servicechathead);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatImage = new ImageView(this);
        chatImage.setImageResource(R.drawable.final12);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        chatImage.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatImage, params);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(chatImage, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatImage != null) windowManager.removeView(chatImage);
    }
}

