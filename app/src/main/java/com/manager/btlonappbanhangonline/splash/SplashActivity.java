package com.manager.btlonappbanhangonline.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.home.cart.CartViewModel;
import com.manager.btlonappbanhangonline.login.LoginActivity;
import com.manager.btlonappbanhangonline.login.setprofile.SetProfileActivity;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        splashViewModel =  new ViewModelProvider(SplashActivity.this).get(SplashViewModel.class);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(Exception ex){

                }finally {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            splashViewModel.currentUser.observe(SplashActivity.this, user -> {
                                if (user != null) {
                                    splashViewModel.checkData().observe(SplashActivity.this, new Observer<Boolean>() {
                                        @Override
                                        public void onChanged(Boolean aBoolean) {
                                            if(aBoolean){
                                                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(home);
                                                finish();
                                            } else {
                                                Intent home = new Intent(getApplicationContext(), SetProfileActivity.class);
                                                home.putExtra("start", "login");
                                                startActivity(home);
                                                finish();
                                            }
                                        }
                                    });
                                } else {
                                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(login);
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        };
        thread.start();
    }
}