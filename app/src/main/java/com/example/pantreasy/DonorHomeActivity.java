package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DonorHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.updateGlobals(this, "Catalyst Cafe");
        setContentView(R.layout.donor_home);
        ImageButton profileButton = findViewById(R.id.profile_icon);
        Button addNewDonation = findViewById(R.id.new_donation_button);
        Button pantryResponses = findViewById(R.id.view_responses_button);
        Button donationHistory = findViewById(R.id.donation_history_button);
        ImageButton profileIcon = findViewById(R.id.profile_icon);

        addNewDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewDonation = new Intent(DonorHomeActivity.this, DonorAddActivity.class);
                DonorHomeActivity.this.startActivity(addNewDonation);
            }
        });

        pantryResponses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewResponses = new Intent(DonorHomeActivity.this, DonorsPantryResponsesActivities.class);
                DonorHomeActivity.this.startActivity(viewResponses);
            }
        });

        donationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewHistory = new Intent(DonorHomeActivity.this, DonorHistoryActivity.class);
                DonorHomeActivity.this.startActivity(viewHistory);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(DonorHomeActivity.this, ProfileActivity.class);
                DonorHomeActivity.this.startActivity(goToProfile);
            }
        });

    }


}
