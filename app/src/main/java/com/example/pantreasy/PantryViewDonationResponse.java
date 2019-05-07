package com.example.pantreasy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class PantryViewDonationResponse extends AppCompatActivity {
    private FirebaseManager mFirebaseManager;
    private ConstraintLayout mLayout;
    private TextView mDonorName;
    private RecyclerView mRecyclerView;
    private TextView mPickupOrDropoffText;
    private TextView mDateTime;
    private TextView mAddress;
    private ImageButton mHomeButton;
    private String mDonationUUID;
    private DonationItem mDonationItem;
    private PantryFoodItemAdapter mAdapter;
    private Profile mProfile;
    private ValueEventListener mValueEventListener;
    private ValueEventListener mProfileListener;
    private TextView mConfirmedText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_view);

        mFirebaseManager = new FirebaseManager(this);
        initValueEventListeners();
        initializeDonationItem();
    }

    private void initializeProfile() {
        mFirebaseManager.getProfile(mDonationItem.profileName, mProfileListener);
    }

    private void initValueEventListeners() {
        mProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProfile = mFirebaseManager.getProfileFromDataSnapshot(dataSnapshot);
                mLayout = findViewById(R.id.pantry_response_view);
                mDonorName = mLayout.findViewById(R.id.donor_name_pantry_view_donation);
                mRecyclerView = mLayout.findViewById(R.id.recycler_view);
                mPickupOrDropoffText = mLayout.findViewById(R.id.pickup_dropoff_text2);
                mDateTime = mLayout.findViewById(R.id.date_time_text);
                mAddress = mLayout.findViewById(R.id.address_text);
                mHomeButton = mLayout.findViewById(R.id.home_button);
                mConfirmedText = mLayout.findViewById(R.id.confirmed_text);

                mDonorName.setText(mDonationItem.profileName);
                if (mDonationItem.pickup)
                    mPickupOrDropoffText.setText("Pick-Up");
                else
                    mPickupOrDropoffText.setText("Drop-Off");
                mDateTime.setText(mDonationItem.time);
                mAddress.setText(mProfile.address);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(PantryViewDonationResponse.this));
                setOnClickForHomeButton();
//                if (mDonationItem.confirmed < 0) {
//                    mConfirmedText.setText("Rejected");
//                    mConfirmedText.setBackgroundColor(Color.RED);
//                } else if (mDonationItem.confirmed == 0) {
//                    mConfirmedText.setText("Awaiting Response");
//                    mConfirmedText.setBackgroundColor(Color.GRAY);
//                } else {
//                    mConfirmedText.setText("Confirmed");
//                    mConfirmedText.setBackgroundColor(Color.GREEN);
//                }
                setAdapterAndUpdateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDonationItem = mFirebaseManager.getDonationFromDataSnapshot(dataSnapshot);
                initializeProfile();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void initializeDonationItem() {
        Intent goToSecondActivityIntent = getIntent();
        Bundle intentExtras = goToSecondActivityIntent.getExtras();
        if(intentExtras!=null) {
            mDonationUUID = (String) intentExtras.get("donationUUID");
            mFirebaseManager.getDonation(mDonationUUID, mValueEventListener);
        }
    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(PantryViewDonationResponse.this, PantryHomeActivity.class);
                PantryViewDonationResponse.this.startActivity(goHome);
            }
        });
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new PantryFoodItemAdapter(this, mDonationItem.foodItems, false, true);
        mRecyclerView.setAdapter(mAdapter);
    }


}
