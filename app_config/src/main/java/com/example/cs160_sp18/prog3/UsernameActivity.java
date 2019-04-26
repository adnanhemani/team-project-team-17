package com.example.cs160_sp18.prog3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsernameActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Login Here");
        toolbar.setTitleMarginStart(50);
        Button submit_username = findViewById(R.id.submit_username);
        final EditText username_et = findViewById(R.id.user_name);
        final EditText password_et = findViewById(R.id.password);

        submit_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                String username = username_et.getText().toString();
                String password = password_et.getText().toString();
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(UsernameActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent goToBearViewIntent = new Intent(UsernameActivity.this, BearView.class);
                                    goToBearViewIntent.putExtra("username", user.getEmail());
                                    UsernameActivity.this.startActivity(goToBearViewIntent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(UsernameActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
