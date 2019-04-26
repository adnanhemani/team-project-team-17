package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DonorHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_home);
        Button addNewDonation = findViewById(R.id.view_donations);
        Button pantryResponses = findViewById(R.id.donor_responses2);
        Button donationHistory = findViewById(R.id.donation_history);

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

    }


}
