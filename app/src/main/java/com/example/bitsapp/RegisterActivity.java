package com.example.bitsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextView btn;
    private EditText inputUsername,inputPassword,inputEmail,inputConformPassword;
    Button btnRegister;


    private FirebaseAuth mAuth;
    private ProgressBar mLoadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn=findViewById(R.id.alreadyHaveAccount);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConformPassword = findViewById(R.id.inputConformPassword);

        mAuth = FirebaseAuth.getInstance();
        //mLoadingBar = new ProgressBar(RegisterActivity.this , null  );

        mLoadingBar = new ProgressBar(RegisterActivity.this, null, android.R.attr.progressBarStyleLarge);
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        //params.addRule(RelativeLayout.CENTER_IN_PARENT);



        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void checkCredentials() {
        String username=inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conformPassword = inputConformPassword.getText().toString();

        if(username.isEmpty() || username.length()<7)
        {
            showError(inputUsername , "Your username is not valid");
        }

        else if(email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail , "Email is invalid ");
        }

        else if(password.isEmpty() || password.length()<7)
        {
            showError(inputPassword , "Password must be 7 characters");
        }

        else if (conformPassword.isEmpty() || !conformPassword.equals(password))
        {
            showError(inputConformPassword , "Password doesnt match");
        }
        else
        {
            //mLoadingBar.("Registration");
           // mLoadingBar.("Please wait, check your credentials");
            //mLoadingBar.setCancelable(false);
            mLoadingBar.setVisibility(View.VISIBLE);


            mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {

                        Toast.makeText(RegisterActivity.this , "Successful" , Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this , SetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, task.getException().toString() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(EditText input , String s)
    {
        input.setError(s);
        input.requestFocus();
    }
}
