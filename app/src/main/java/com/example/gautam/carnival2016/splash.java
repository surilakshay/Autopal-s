package com.example.gautam.carnival2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.Window;

/**
 * Created by gautam on 04-06-2016.
 */
public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread t = new Thread(){

            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent i = new Intent(splash.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        t.start();
    }
}
