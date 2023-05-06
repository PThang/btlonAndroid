package com.manager.btlonappbanhangonline.login.login.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.login.LoginActivity;
import com.manager.btlonappbanhangonline.login.login.ResultCallBack;
import com.manager.btlonappbanhangonline.login.setprofile.SetProfileActivity;

public class LoginViewModel extends AndroidViewModel {
    private FirebaseAuth auth;
    private ResultCallBack loginResultCallback;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null && user.getDisplayName() != null) {
                                Intent intent = new Intent(getApplication().getApplicationContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("start", "login");
                                getApplication().startActivity(intent);
                                loginResultCallback.onLoginSuccess();
                            } else {
                                Intent intent = new Intent(getApplication().getApplicationContext(), SetProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplication().startActivity(intent);
                                loginResultCallback.onLoginFailure("User display name is null.");
                            }
                            Log.d("Register State:", "Success");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Register State:", "Failed");
                        Toast.makeText(getApplication(), "Password or email is wrong.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(getApplication(), "Password or email is wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setLoginResultCallback(ResultCallBack callback) {
        loginResultCallback = callback;
    }
}
