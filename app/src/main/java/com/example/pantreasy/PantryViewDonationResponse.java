package com.example.pantreasy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PantryViewDonationResponse extends AppCompatActivity {
    private ConstraintLayout mLayout;
    private TextView mDonorName;
    private RecyclerView mRecyclerView;
    private TextView mPickupOrDropoffText;
    private TextView mDateTime;
    private TextView mAddress;
    private ImageButton mHomeButton;
    private DonationItem mDonationItem;
    private PantryFoodItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_view);

        mLayout = findViewById(R.id.pantry_response_view);
        mDonorName = mLayout.findViewById(R.id.donor_name_pantry_view_donation);
        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mPickupOrDropoffText = mLayout.findViewById(R.id.pickup_dropoff_text2);
        mDateTime = mLayout.findViewById(R.id.date_time_text);
        mAddress = mLayout.findViewById(R.id.address_text);
        mHomeButton = mLayout.findViewById(R.id.home_button);

        initializeDonationItem();
        //mDonorName.setText(mDonationItem.profile.name);
        if (mDonationItem.pickup)
            mPickupOrDropoffText.setText("Pick-Up");
        else
            mPickupOrDropoffText.setText("Drop-Off");
        mDateTime.setText(mDonationItem.time);
        //mAddress.setText(mDonationItem.profile.address);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnClickForHomeButton();

        setAdapterAndUpdateData();
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

    private void initializeDonationItem() {
        List<FoodItem> foodItems = new ArrayList<FoodItem>();
        Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.pantry_a_profile_pic);
//        foodItems.add(new FoodItem("Eggs", icon1, "2/2/22", "5", true));
//        foodItems.add(new FoodItem("Butter", icon1, "2/2/22", "5", true));
//        foodItems.add(new FoodItem("Cheese", icon1, "2/2/22", "5", true));
//        foodItems.add(new FoodItem("Noodles", icon1, "2/2/22", "5", true));
//        foodItems.add(new FoodItem("Beef", icon1, "2/2/22", "5", true));


        Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_a_profile_pic);
        Profile p1 = new Profile("PLACEHOLDER", "Donor A", "555-420-1337", "1777 Hearst Ave", "We are donor A", null, null);

        //mDonationItem = new DonationItem(p1, foodItems, "No comments", "4:00pm", true);
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new PantryFoodItemAdapter(this, mDonationItem.foodItems, false);
        mRecyclerView.setAdapter(mAdapter);
    }


}
