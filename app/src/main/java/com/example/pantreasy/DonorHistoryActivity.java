package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorHistoryActivity extends AppCompatActivity {

    private ImageButton mHomeButton;
    private FirebaseManager fm = new FirebaseManager(DonorHistoryActivity.this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donor_history_view);
        final List<DonationItem> dataForAdapter = new ArrayList<DonationItem>();
        ((Pantreasy)getApplication()).getCurrentProfile();
        for (String uuid: ((Pantreasy)getApplication()).getCurrentProfile().postedDonationUUIDs) {
            fm.getDonation(uuid, new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataForAdapter.add(fm.getDonationFromDataSnapshot(dataSnapshot));
                    ((Pantreasy)getApplication()).setPostedDonations(dataForAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        mHomeButton = findViewById(R.id.home_button);
        setOnClickForHomeButton();

    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(DonorHistoryActivity.this, DonorHomeActivity.class);
                DonorHistoryActivity.this.startActivity(goHome);
            }
        });
    }
}
