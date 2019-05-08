package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button donorLogin = findViewById(R.id.donor_account);
        Button pantryLogin = findViewById(R.id.pantry_account);
        Utils.updateGlobals(this, "Berkeley Food Pantry");
        Utils.updateListOfAllDonationsAndProfiles(this);

        donorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDonorHome = new Intent(Login.this, DonorHomeActivity.class);
                Login.this.startActivity(goToDonorHome);

            }
        });

        pantryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPantryHome = new Intent(Login.this, PantryHomeActivity.class);
                Login.this.startActivity(goToPantryHome);
            }
        });
    }
}
