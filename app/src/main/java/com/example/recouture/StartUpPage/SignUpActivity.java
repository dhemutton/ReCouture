package com.example.recouture.StartUpPage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "SignUpActivity";

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private EditText editTextConfirmPassword;

    private ActivityIndicator dialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(SignUpActivity.this, HomepageActivity.class));
        }

        dialog = new ActivityIndicator(this);

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        textViewSignin = findViewById(R.id.textViewSignin);
        buttonRegister.setOnClickListener(this);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this,"Passwords dont match",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        // if validations ok
        // register user
        dialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            //user is successfully registered and logged in
                            //we will start the profile activity here
                            Toast.makeText(SignUpActivity.this,"Account Registered",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),SetUpProfile.class));
                        } else {
                            Toast.makeText(SignUpActivity.this,"Could not register... please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             e.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
        if (view == textViewSignin) {
            // will open login activity here
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

}
