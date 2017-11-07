package com.stjohns.stormchat.Activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.stjohns.stormchat.R;

public class LoginActivity extends Activity {

    private FirebaseAuth loginAuthenticator;
    private Intent toHome = new Intent(this, HomeActivity.class);

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
            startActivity(toHome);
        }
    }

    public void createAccount(String email, String password){
        loginAuthenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Creation success

                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    public void signIn(String email, String password){
        loginAuthenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, switch activites
                            startActivity(toHome);
                            LoginActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }
}
