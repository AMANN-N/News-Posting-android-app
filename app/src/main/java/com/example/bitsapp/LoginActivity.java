package com.example.bitsapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity<getResult, val> extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    TextView btn;
    EditText inputEmail , inputPassword;
    Button btnLogin;

    private FirebaseAuth mAuth;
    ProgressBar mLoadingBar;

    //private Button btnGoogle;
    //GoogleSignInOptions gso;
    //GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.textViewSignUp);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnlogin);
        //btnGoogle=findViewById(R.id.btnGoogle);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        mAuth=FirebaseAuth.getInstance();
        mLoadingBar = new ProgressBar(LoginActivity.this);

       // gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                ////.requestIdToken(getString("169668020701-1u1avoro0sa2lo5umrqqkre3v6295u0j.apps.googleusercontent.com"))
               // .requestEmail()
               // .build();

       // mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

       // btnGoogle.setOnClickListener(new View.OnClickListener() {
           // @Override
          //  public void onClick(View view) {
        //        SignIn();

         //   }
      //  });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });



    }


    private void checkCredentials() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();




        if(email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail , "Email is invalid ");
        }

        else if(password.isEmpty() || password.length()<7)
        {
            showError(inputPassword , "Password must be 7 characters");
        }


        else
        {
            mLoadingBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent=new Intent(LoginActivity.this , MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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
/*
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            updateUI();
        }
    }
*/
   // private void SignIn() {
   //     Intent signInIntent = mGoogleSignInClient.getSignInIntent();
   //     startActivityForResult(signInIntent , RC_SIGN_IN);

   // }
    /*
    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);




            if(task.isSuccessful()) {

                Toast.makeText(getApplicationContext(), "Google sign in successful", Toast.LENGTH_SHORT).show();
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount=task
                            .getResult(ApiException.class);
                    // Check condition
                    if(googleSignInAccount!=null)
                    {
                        // When sign in account is not equal to null
                        // Initialize auth credential
                        AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        ,null);
                        // Check credential
                        mAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check condition
                                        if(task.isSuccessful())
                                        {
                                            // When task is successful
                                            updateUI(null);
                                            // Display Toast
                                            Toast.makeText(getApplicationContext(), "Firebase authentication successful", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            // When task is unsuccessful
                                            try {
                                                Toast.makeText(getApplicationContext(), "Authentication Failed :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            catch (Exception e){
                                                e.getStackTrace();
                                            }
                                        }
                                    }
                                });

                    }
                } catch (ApiException e) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Authentication Failed :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


/*

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }




    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, user.getEmail() + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }
*/
    private void updateUI(FirebaseUser user) {

        finish();
        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        startActivity(intent);
    }


}
