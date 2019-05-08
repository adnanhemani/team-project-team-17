package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button donorLogin = findViewById(R.id.donor_account);
        Button pantryLogin = findViewById(R.id.pantry_account);
        final EditText username_et = findViewById(R.id.input1);
        final EditText password_et = findViewById(R.id.input2);
        final FirebaseManager fm = new FirebaseManager(this);

        donorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(username_et.getText().toString(), password_et.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Utils.updateGlobals(Login.this, "Berkeley Food Pantry");
                            Utils.updateListOfAllDonationsAndProfiles(Login.this);
                            Intent goToDonorHome = new Intent(Login.this, DonorHomeActivity.class);
                            Login.this.startActivity(goToDonorHome);
                        } else {
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        pantryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(username_et.getText().toString(), password_et.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent goToPantryHome = new Intent(Login.this, PantryHomeActivity.class);
                            Login.this.startActivity(goToPantryHome);
                        } else {
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
