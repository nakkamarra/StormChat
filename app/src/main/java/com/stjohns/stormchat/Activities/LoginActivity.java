package com.stjohns.stormchat.Activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.stjohns.stormchat.R;

public class LoginActivity extends Activity {

    private FirebaseAuth loginAuthenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginAuthenticator = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = loginAuthenticator.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        Button loginButton = (Button) findViewById(R.id.login_button);
        Button createAccountButton = (Button) findViewById(R.id.create_account_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(R.id.login_button).show();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(R.id.create_account_button).show();
            }
        });
    }

    public void createAccount(String email, String password){
        loginAuthenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginAuthenticator.getInstance().getCurrentUser().sendEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    public void logIn(String email, String password){
        loginAuthenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (loginAuthenticator.getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                LoginActivity.this.finish();
                            }
                            else{
                                //Display user not email verified
                            }
                        } else {
                            //Display sign in failed
                        }
                    }
                });
    }

    public AlertDialog createDialog(int id){
        final EditText emailField = new EditText(this);
        final EditText passwordField = new EditText(this);
        emailField.setMaxLines(1);
        passwordField.setMaxLines(1);

        LinearLayout inputs = new LinearLayout(this);
        inputs.setOrientation(LinearLayout.VERTICAL);
        inputs.addView(emailField);
        inputs.addView(passwordField);

        AlertDialog.Builder inputDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        inputDialogBuilder.setView(inputs);
        switch (id){
            case R.id.login_button:
                inputDialogBuilder.setTitle("Log In");
                inputDialogBuilder.setMessage("Please provide your credentials:");
                inputDialogBuilder.setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logIn(emailField.getText().toString(), passwordField.getText().toString());
                    }
                });
                break;
            case R.id.create_account_button:
                inputDialogBuilder.setTitle("Create Account");
                inputDialogBuilder.setMessage("Use your St. John's University email to create an account." +
                        "A verification email will be sent to the specified address. Once verified, you will" +
                        "be able to sign in using your new credentials." );
                inputDialogBuilder.setPositiveButton("Create Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createAccount(emailField.getText().toString(), passwordField.getText().toString());
                    }
                });
                break;
        }

        inputDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return inputDialogBuilder.create();
    }

}
