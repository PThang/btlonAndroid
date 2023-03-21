package com.manager.btlonappbanhangonline.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.manager.btlonappbanhangonline.R;

public class LienHeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        Button button = findViewById(R.id.btnFb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("100008841048121");
            }
        });
    }

    private void goToFacebook(String id) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+id));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+id));
            startActivity(intent);
        }
    }
}