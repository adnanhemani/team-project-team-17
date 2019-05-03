package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PantryHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_home);

        Button viewResponses = findViewById(R.id.view_responses_button);
        Button donationHistory = findViewById(R.id.donation_history_button);
        Button viewDonations = findViewById(R.id.view_donations_button);

        viewResponses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToViewDonations = new Intent(PantryHomeActivity.this, PantryViewResponsesActivity.class);
                PantryHomeActivity.this.startActivity(goToViewDonations);
            }
        });
        viewDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToViewDonations = new Intent(PantryHomeActivity.this, PantryViewDonationsActivity.class);
                PantryHomeActivity.this.startActivity(goToViewDonations);
            }
        });

        donationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDonationResponses = new Intent(PantryHomeActivity.this, PantryViewResponsesActivity.class);
                PantryHomeActivity.this.startActivity(goToDonationResponses);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(PantryHomeActivity.this, Profile.class);
                PantryHomeActivity.this.startActivity(goToProfile);
            }
        });
    }
}
