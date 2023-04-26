package com.manager.btlonappbanhangonline.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.manager.btlonappbanhangonline.R;


import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(Exception ex){

                }finally {
                    if(Paper.book().read("user")==null){
                        /*Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();*/
                        //Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        Intent home = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(home);
                        finish();
                    }else{
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}