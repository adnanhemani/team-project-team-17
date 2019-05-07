package com.example.pantreasy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.ValueEventRegistration;

import java.util.ArrayList;
import java.util.List;

public class PantryViewResponsesActivity extends AppCompatActivity {

    private List<DonationItem> mDonations;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private PantryResponseItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private FirebaseManager mFirebaseManager;
    private ValueEventListener mResponseUUIDListener;
    private ValueEventListener mDonationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_list_view);

        mFirebaseManager = new FirebaseManager(this);
        mDonations = new ArrayList<DonationItem>();
        initializeValueEventListeners();
        mLayout = findViewById(R.id.pantry_response_list);
        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);

        setOnClickForHomeButton();
    }

    private void initializeValueEventListeners() {
        mDonationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DonationItem d = mFirebaseManager.getDonationFromDataSnapshot(dataSnapshot);
                if (d != null)
                    mDonations.add(d);
                setAdapterAndUpdateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mResponseUUIDListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> donationUUIDs = mFirebaseManager.getDonationUUIDsFromSnapshot(dataSnapshot);
                for (int i = 0; i < donationUUIDs.size(); i++) {
                    mFirebaseManager.getDonation(donationUUIDs.get(i), mDonationListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };



        mFirebaseManager.getRequestUUIDs("Berkeley Food Pantry", mResponseUUIDListener);


    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(PantryViewResponsesActivity.this, PantryHomeActivity.class);
                PantryViewResponsesActivity.this.startActivity(goHome);
            }
        });
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new PantryResponseItemAdapter(this, mDonations);
        mRecyclerView.setAdapter(mAdapter);
    }
}
