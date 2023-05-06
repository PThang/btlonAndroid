package com.manager.btlonappbanhangonline.login.login.register;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.login.login.ResultCallBack;
import com.manager.btlonappbanhangonline.login.setprofile.SetProfileActivity;

public class RegisterViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    private ResultCallBack resultCallback;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void register(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Log.i("Register State:", "Success");
                            Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication().getApplicationContext(), SetProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplication().startActivity(intent);
                            resultCallback.onLoginSuccess();
                        } else {
                            Log.i("Register State:", "Failed");
                            Toast.makeText(getApplication(), "Fail", Toast.LENGTH_SHORT).show();
                            resultCallback.onLoginFailure("User display name is null.");
                        }
                    }
                });
    }

    public void setRegisterResultCallback(ResultCallBack callback) {
        resultCallback = callback;
    }
}
