package com.stjohns.stormchat.Activities;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stjohns.stormchat.Objects.User.User;
import com.stjohns.stormchat.R;

public class LoginActivity extends Activity
{

    private FirebaseAuth loginAuthenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
            LoginActivity.this.finish();
        }

        Button loginButton = findViewById(R.id.login_button);
        Button createAccountButton = findViewById(R.id.create_account_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasWindowFocus()) {
                    createDialog(R.id.login_button).show();
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasWindowFocus()) {
                    createDialog(R.id.create_account_button).show();
                }
            }
        });
    }

    public void createAccount(String email, String password){
        if (passwordIsStrong(password)){
            if (emailIsSjuDomain(email)){
                loginAuthenticator.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    currentUser.sendEmailVerification();
                                    /////////// INSTANTIATE A USER OBJECT AND SEND TO DATABASE /////////////
                                    User dummyUser = new User(
                                            "N/A",
                                            currentUser.getEmail(),
                                            "https://firebasestorage.googleapis.com/v0/b/stormchatsju.appspot.com/o/User_Profile%2Fuser.png?alt=media&token=07de37e6-6672-4f6a-9677-82a877c29ec3",
                                            "I'm using StormChat! Send me a Message!",
                                            currentUser.getUid(),
                                            "N/A",
                                            "N/A");
                                    createUserInDatabase(dummyUser, currentUser);
                                    Toast.makeText(LoginActivity.this, R.string.create_account_success, Toast.LENGTH_LONG).show();
                                    ///////////////////////////////////////////////////////////////////////
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.create_account_failed, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else
                Toast.makeText(LoginActivity.this, R.string.invalid_email_domain, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(LoginActivity.this, R.string.weak_password, Toast.LENGTH_LONG).show();
    }

    public void logIn(String email, String password){
        loginAuthenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (loginAuthenticator.getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                LoginActivity.this.finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, R.string.email_not_verified, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.log_in_failed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public Dialog createDialog(int id){
        final Dialog loginDialog = new Dialog(LoginActivity.this);
        loginDialog.setContentView(R.layout.login_dialog);

        final EditText emailField = loginDialog.findViewById(R.id.login_email_field);
        final EditText passwordField = loginDialog.findViewById(R.id.login_password_field);
        final TextView dialogTitle = loginDialog.findViewById(R.id.login_dialog_title);
        final TextView dialogMessage = loginDialog.findViewById(R.id.login_dialog_message);
        final Button dialogPositiveButton = loginDialog.findViewById(R.id.login_dialog_positive);
        final Button dialogNegativeButton = loginDialog.findViewById(R.id.login_dialog_negative);

        switch (id){
            case R.id.login_button:
                dialogTitle.setText(R.string.log_in);
                dialogMessage.setText(R.string.login_message);
                dialogPositiveButton.setText(R.string.log_in);
                dialogPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logIn(emailField.getText().toString(), passwordField.getText().toString());
                        loginDialog.dismiss();
                    }
                });
                break;
            case R.id.create_account_button:
                dialogTitle.setText(R.string.create_account);
                dialogMessage.setText(R.string.create_account_message );
                dialogPositiveButton.setText(R.string.create_account);
                dialogPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createAccount(emailField.getText().toString(), passwordField.getText().toString());
                        loginDialog.dismiss();
                    }
                });
                break;
        }

        dialogNegativeButton.setText(R.string.cancel);
        dialogNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });

        loginDialog.setCancelable(true);
        loginDialog.setCanceledOnTouchOutside(true);

        return loginDialog;
    }

    public boolean passwordIsStrong(String password){
       boolean valid = false;
           int digitCount = 0;
           for (char letter : password.toCharArray()){
               if (Character.isDigit(letter)){
                   digitCount++;
               }
           }
           if(password.length() > 6 && digitCount >= 1){
               valid = true;
           }
       return valid;
    }

    public boolean emailIsSjuDomain(String email){
        boolean valid = false;
            if (email.indexOf('@') != -1){
                String emailSubstring = email.substring(email.indexOf('@'), email.length());
                if (emailSubstring.equalsIgnoreCase("@stjohns.edu"))
                    valid = true;
            }
        return valid;
    }

    public void createUserInDatabase(User u, FirebaseUser currentFBUser){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users").child(currentFBUser.getUid());
        database.child("email").setValue(u.getEmail());
        database.child("username").setValue(u.getName());
        database.child("status").setValue(u.getBio());
        database.child("college").setValue(u.getMajor());
        database.child("major").setValue(u.getMajor());
        database.child("imageurl").setValue(u.getImagePath());
    }
}
