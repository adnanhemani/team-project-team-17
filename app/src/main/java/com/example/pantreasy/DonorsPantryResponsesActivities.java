package com.example.pantreasy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.util.ArrayList;

public class DonorsPantryResponsesActivities extends AppCompatActivity {
    private ArrayList<DonorResponseItem> mResponseItems;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private DonorResponseItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private ImageButton mConfirmButton;
    private ImageView mBlurredBackground;
    private CardView mConfirmationPopup;

    private Button mPopupOkayButton;
    private Button mAddAnotherDonationButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_response_view);

        mResponseItems = new ArrayList<DonorResponseItem>();
        mLayout = findViewById(R.id.donor_response_view);
        mRecyclerView = mLayout.findViewById(R.id.response_items_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);
        mConfirmButton = mLayout.findViewById(R.id.response_view_confirm_button);
        mBlurredBackground = mLayout.findViewById(R.id.blurred_background_response_view);
        mConfirmationPopup = mLayout.findViewById(R.id.response_confirmed_popup);
        mPopupOkayButton = mConfirmationPopup.findViewById(R.id.ok_button);
        mAddAnotherDonationButton = mConfirmationPopup.findViewById(R.id.add_another_donation_button);
        mBlurredBackground.setVisibility(View.GONE);
        mConfirmationPopup.setVisibility(View.GONE);


        generateResponseItems();
        setOnClickForHomeButton();
        setOnClickForConfirmButton();
        setOnClickforPopupButtons();

    }

    //TODO
    // Replace this with actual code pulling these from firebase
    public void generateResponseItems() {
        Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.pantry_a_profile_pic);
        Profile p1 = new Profile("PLACEHOLDER", "Pantry A", "555-420-1337", "1777 Hearst Ave", "We are food pantry A", null, null);

        Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.pantry_b_profile_pic);
        Profile p2 = new Profile("PLACEHOLDER", "Pantry B", "555-555-5555", "1777 Le Roy Ave", "We are food pantry B", null, null);

        Bitmap icon3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.pantry_c_profile_pic);
        Profile p3 = new Profile("PLACEHOLDER", "Pantry C", "555-600-2020", "2222 Shattuck Ave", "We are food pantry C", null, null);

        //mResponseItems.add(new DonorResponseItem(p1, "We would like to pickup just the bananas please! Give us a call!"));
        //mResponseItems.add(new DonorResponseItem(p2, "We will take everything you're offering! Thank you."));
        //mResponseItems.add(new DonorResponseItem(p3, "We would like the bread!"));

        setAdapterAndUpdateData();
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
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new DonorResponseItemAdapter(this, this.mResponseItems);
        mRecyclerView.setAdapter(mAdapter);
    }
}
