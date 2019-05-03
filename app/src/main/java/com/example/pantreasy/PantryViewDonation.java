package com.example.pantreasy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.ValueEventRegistration;

import java.util.ArrayList;
import java.util.List;

public class PantryViewDonation extends AppCompatActivity {
    private FirebaseManager mFirebaseManager;
    private ConstraintLayout mLayout;
    private TextView mDonorName;
    private RecyclerView mRecyclerView;
    private TextView mPickupOrDropoffText;
    private TextView mDateTime;
    private TextView mAddress;
    private Button mConfirmButton;
    private ImageButton mHomeButton;
    private DonationItem mDonationItem;
    private PantryFoodItemAdapter mAdapter;
    private ImageView mBlurredBackground;
    private CardView mPopupMessage;

    private Button mViewMoreDonations;
    private Button mOkButton;

    private ValueEventListener mValueEventListener;
    private String mDonationUUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_donate_view);

        mFirebaseManager = new FirebaseManager(this);

        mLayout = findViewById(R.id.donor_response_view);
        mDonorName = mLayout.findViewById(R.id.donor_name_pantry_view_donation);
        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mPickupOrDropoffText = mLayout.findViewById(R.id.pickup_dropoff_text2);
        mDateTime = mLayout.findViewById(R.id.date_time_text);
        mAddress = mLayout.findViewById(R.id.address_text);
        mConfirmButton = mLayout.findViewById(R.id.request_donation_button);
        mHomeButton = mLayout.findViewById(R.id.home_button);
        mBlurredBackground = mLayout.findViewById(R.id.blurred_background);
        mPopupMessage = mLayout.findViewById(R.id.pantry_request_sent_layout);

        mViewMoreDonations = mPopupMessage.findViewById(R.id.back_button_popup);
        mOkButton = mPopupMessage.findViewById(R.id.ok_button);

        mBlurredBackground.setVisibility(View.GONE);
        mPopupMessage.setVisibility(View.GONE);
        setValueEventListener();
        initializeDonationItem();
    }

    private void setValueEventListener() {
        mValueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDonationItem = mFirebaseManager.getDonationFromDataSnapshot(dataSnapshot);
                mDonorName.setText(mDonationItem.profileName);
                if (mDonationItem.pickup)
                    mPickupOrDropoffText.setText("Pick-Up");
                else
                    mPickupOrDropoffText.setText("Drop-Off");
                mDateTime.setText(mDonationItem.time);
                //mAddress.setText(mDonationItem.profile.address);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(PantryViewDonation.this));
                setOnClickForHomeButton();
                setOnClickForConfirmButton();
                setOnClickForViewMoreDonationsButton();
                setOnClickForOkButton();

                setAdapterAndUpdateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void setOnClickForOkButton() {
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlurredBackground.setVisibility(View.GONE);
                mPopupMessage.setVisibility(View.GONE);
            }
        });
    }

    private void setOnClickForViewMoreDonationsButton() {
        mViewMoreDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlurredBackground.setVisibility(View.GONE);
                mPopupMessage.setVisibility(View.GONE);
                Intent goToDonations = new Intent(PantryViewDonation.this, PantryViewDonationsActivity.class);
                PantryViewDonation.this.startActivity(goToDonations);
            }
        });
    }

    private void setOnClickForConfirmButton() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setDrawingCacheEnabled(true);
                mLayout.buildDrawingCache();
                Bitmap bm = mLayout.getDrawingCache();
                Bitmap blurBm = Utils.fastblur(bm, 1,60);
                mBlurredBackground.setImageBitmap(blurBm);
                mBlurredBackground.setVisibility(View.VISIBLE);
                mPopupMessage.setVisibility(View.VISIBLE);
                Bitmap icon = BitmapFactory.decodeResource(PantryViewDonation.this.getResources(), R.drawable.pantry_a_profile_pic);
                Profile p = new Profile("Berkeley_Food_Pantry.JPEG", "Berkeley Food Pantry", "510-510-5105", "1111 Berkeley Way", "We are the Berkeley Food Pantry", null, null);
                mFirebaseManager.addProfile(icon, p);
                DonorResponseItem r = new DonorResponseItem("Berkeley Food Pantry", "We would like everything you're offering!", mDonationUUID);
                mFirebaseManager.addResponse("Berkeley Food Pantry", mDonationUUID, r); 
            }
        });
    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(PantryViewDonation.this, PantryHomeActivity.class);
                PantryViewDonation.this.startActivity(goHome);
            }
        });
    }

    private void initializeDonationItem() {
        Intent goToSecondActivityIntent = getIntent();
        Bundle intentExtras = goToSecondActivityIntent.getExtras();
        if(intentExtras!=null) {
            mDonationUUID = (String) intentExtras.get("donation_UUID");
            mFirebaseManager.getDonation(mDonationUUID, mValueEventListener);
        }
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new PantryFoodItemAdapter(this, mDonationItem.foodItems, true);
        mRecyclerView.setAdapter(mAdapter);
    }


}
