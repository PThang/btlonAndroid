package com.manager.btlonappbanhangonline.login.forgetpassword;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassWordViewModel extends AndroidViewModel {
    FirebaseAuth auth;

    public ForgetPassWordViewModel(@NonNull Application application) {
        super(application);

        auth = FirebaseAuth.getInstance();
    }

    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplication().getApplicationContext(), "Vui lòng check email của bạn.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
