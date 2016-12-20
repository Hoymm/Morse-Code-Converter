package com.hoymm.muca.damian.morsecodeconverter2;

import android.app.Activity;
import android.os.Bundle;

public class HoymmLogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoymm_logo);
        Thread openAgainMenu = new Thread(){
          public void run(){
              try {
                  sleep(1200);
                  //startActivity(new Intent(HoymmLogoActivity.this, MainActivity.class));
                  finish();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        };
        openAgainMenu.start();
    }
}
