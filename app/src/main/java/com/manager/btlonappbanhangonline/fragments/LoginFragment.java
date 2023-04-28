package com.manager.btlonappbanhangonline.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.activity.HomeActivity;
import com.manager.btlonappbanhangonline.databinding.FragmentLoginBinding;
import com.manager.btlonappbanhangonline.eventbus.IMoveClickListener;
import com.manager.btlonappbanhangonline.model.User;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    private FirebaseAuth auth;
    private IMoveClickListener moveClickListener;

    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

//    public LoginFragment() {
//        // Required empty public constructor
//    }

    public LoginFragment(IMoveClickListener moveClickListener) {
        this.moveClickListener = moveClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(requireActivity(), gso);
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
                                updateUI(user);
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

        binding.loginWithGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(requireActivity(), HomeActivity.class);
        startActivity(i);
    }

    private void signInWithGoogle() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                auth.signInWithCredential(credential)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        if(task.isSuccessful()){
                                            FirebaseUser user = auth.getCurrentUser();
                                            User user1 = new User();
                                            assert user != null;
                                            user1.setUserId(user.getUid());
                                            user1.setUserName(user.getDisplayName());
                                            user1.setUserPic(user.getPhotoUrl().toString());

                                            Intent intent = new Intent(requireActivity().getApplicationContext(), HomeActivity.class);
                                            intent.putExtra("user", user1);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
            }catch (ApiException e){
                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void moveToHome() {
        requireActivity().finish();
    }
}