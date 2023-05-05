package com.manager.btlonappbanhangonline.login.login.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manager.btlonappbanhangonline.databinding.FragmentLoginBinding;
import com.manager.btlonappbanhangonline.home.HomeActivity;
import com.manager.btlonappbanhangonline.eventbus.IMoveClickListener;
import com.manager.btlonappbanhangonline.login.LoginActivity;
import com.manager.btlonappbanhangonline.login.login.LoginContainerFragment;

import java.util.Objects;

public class LoginFragment extends Fragment{
    FragmentLoginBinding binding;
    private FirebaseAuth auth;
    private IMoveClickListener moveClickListener;

    private Intent intent,intent2;

    public LoginFragment(IMoveClickListener moveClickListener, Intent intent, Intent intent2) {
        this.moveClickListener = moveClickListener;
        this.intent = intent;
        this.intent2 = intent2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(v -> login());

        binding.forgetPassWordText.setOnClickListener(v -> {
            moveClickListener.moveTo();
        });

        binding.isShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.passwordText.setInputType(InputType.TYPE_NULL);
            } else {
                binding.passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        binding.forgetPassWordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    private void forgetPassword() {
        startActivity(intent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    void login(){
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if(email.equalsIgnoreCase("")){
            binding.emailText.setError("");
        }
        if(password.equalsIgnoreCase("")){
            binding.passwordText.setError("");
        }
        if(!password.equalsIgnoreCase("") && !email.equalsIgnoreCase("")) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                //String nameUser = user.getDisplayName().toString().trim();

                                if(user != null && user.getDisplayName() != null){
                                    updateUI();
                                }
                                else {
                                    startActivity(intent2);
                                }
                                Log.d("Register State:", "Success");
                            } else {
                                Log.d("Register State:", "Failed");
                                Toast.makeText(requireActivity(), "Password or email is wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(requireActivity(), "Password or email is wrong.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        binding.forgetPassWordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateUI() {
        Intent i = new Intent(requireActivity(), HomeActivity.class);
        startActivity(i);
    }
}