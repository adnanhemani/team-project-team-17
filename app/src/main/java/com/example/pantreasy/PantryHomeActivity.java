package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PantryHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_home);

        Button viewDonations = findViewById(R.id.view_donations);
        Button donorResponses = findViewById(R.id.donor_responses2);

        viewDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToViewDonations = new Intent(PantryHomeActivity.this, PantryViewDonationsActivity.class);
                PantryHomeActivity.this.startActivity(goToViewDonations);
            }
        });

        donorResponses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDonationResponses = new Intent(PantryHomeActivity.this, PantryViewResponsesActivity.class);
                PantryHomeActivity.this.startActivity(goToDonationResponses);
            }
        });
    }
}
