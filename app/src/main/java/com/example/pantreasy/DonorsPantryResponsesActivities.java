package com.example.pantreasy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorsPantryResponsesActivities extends AppCompatActivity {
    private FirebaseManager mFirebaseManager;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private DonorResponseItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private ImageButton mConfirmButton;
    private ImageView mBlurredBackground;
    private CardView mConfirmationPopup;

    private Button mPopupOkayButton;
    private Button mAddAnotherDonationButton;

    private ArrayList<DonorResponseItem> mResponseItems;

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_response_view);

        mFirebaseManager = new FirebaseManager(this);
        mLayout = findViewById(R.id.donor_response_view);
        mRecyclerView = mLayout.findViewById(R.id.response_items_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);
        mConfirmButton = mLayout.findViewById(R.id.check_button_wrapper);
        mBlurredBackground = mLayout.findViewById(R.id.blurred_background_response_view);
        mConfirmationPopup = mLayout.findViewById(R.id.response_confirmed_popup);
        mPopupOkayButton = mConfirmationPopup.findViewById(R.id.ok_button);
        mAddAnotherDonationButton = mConfirmationPopup.findViewById(R.id.add_another_donation_button);
        mBlurredBackground.setVisibility(View.GONE);
        mConfirmationPopup.setVisibility(View.GONE);

        setOnClickForHomeButton();
        setOnClickForConfirmButton();
        setOnClickforPopupButtons();

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initResponseItems();
                setAdapterAndUpdateData();
            }
        };
        getApplication().registerReceiver(mReceiver, new IntentFilter(((Pantreasy)getApplicationContext()).USER_DATA_FILTER));
        if (((Pantreasy)getApplication()).getCurrentProfile() == null) {
            Utils.updateGlobals(this, "Catalyst Cafe");
        }
        else {
            initResponseItems();
            setAdapterAndUpdateData();
        }
    }

    private void initResponseItems() {
        mResponseItems = new ArrayList<>();
        Pantreasy p = ((Pantreasy)getApplication());

        List<DonationItem> donations = ((Pantreasy)getApplication()).getPostedDonations();
        for (DonationItem d : donations) {
            mResponseItems.addAll(d.responseItems);
        }
    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeActivity = new Intent(DonorsPantryResponsesActivities.this, DonorHomeActivity.class);
                DonorsPantryResponsesActivities.this.startActivity(homeActivity);
            }
        });
    }

    private void setOnClickForConfirmButton() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonorResponseItem responseItem = ((DonorResponseItemAdapter)mAdapter).mSelectedItem;
                DonationItem donation = null;
                for (DonationItem d : ((Pantreasy)getApplication()).getPostedDonations()) {
                    if (d.UUID.equals(responseItem.donationUUID))
                        donation = d;
                }
                for (DonorResponseItem response : donation.responseItems) {
                    if (response.UUID != responseItem.UUID)
                        mFirebaseManager.denyDonation(responseItem, donation);
                }
                mFirebaseManager.confirmDonation(responseItem, donation);
                ConstraintLayout view = (ConstraintLayout) findViewById(R.id.donor_response_view);
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                Bitmap bm = view.getDrawingCache();
                Bitmap blurBm = Utils.fastblur(bm, 1,60);
                mBlurredBackground.setImageBitmap(blurBm);
                mBlurredBackground.setVisibility(View.VISIBLE);
                mConfirmationPopup.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOnClickforPopupButtons() {
        mPopupOkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlurredBackground.setVisibility(View.GONE);
                mConfirmationPopup.setVisibility(View.GONE);
                setAdapterAndUpdateData();
            }
        });
        mAddAnotherDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDonationActivity = new Intent(DonorsPantryResponsesActivities.this, DonorAddActivity.class);
                DonorsPantryResponsesActivities.this.startActivity(addDonationActivity);
            }
        });

    }

    private void setAdapterAndUpdateData() {
        if (mResponseItems == null) return;
        mAdapter = new DonorResponseItemAdapter(this, this.mResponseItems);
        mRecyclerView.setAdapter(mAdapter);
    }
}
