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

import java.util.ArrayList;

public class PantryViewDonationsActivity extends AppCompatActivity {

    private ArrayList<DonationItem> mDonations;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private DonationItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private ImageButton mConfirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_donate_list_view);

        mDonations = new ArrayList<DonationItem>();

        mLayout = findViewById(R.id.donor_response_view);
        mRecyclerView = mLayout.findViewById(R.id.donation_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);
        mConfirmButton = mLayout.findViewById(R.id.donate_list_confirm_button);

        setOnClickForHomeButton();
        setOnClickForConfirmButton();

        generateDonationItems();
    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(PantryViewDonationsActivity.this, PantryHomeActivity.class);
                PantryViewDonationsActivity.this.startActivity(goHome);
            }
        });
    }

    private void setOnClickForConfirmButton() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new DonationItemAdapter(this, mDonations);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void generateDonationItems() {
        ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
        foodItems.add(new FoodItem("Eggs", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Butter", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Cheese", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Noodles", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Beef", null, "2/2/22", "5", true));


        Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_a_profile_pic);
        Profile p1 = new Profile(icon1, "Donor A", "555-420-1337", "1777 Hearst Ave", "We are donor A");

        Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_b_profile_pic);
        Profile p2 = new Profile(icon2, "Donor B", "555-555-5555", "1777 Le Roy Ave", "We are donor B");

        Bitmap icon3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_c_profile_pic);
        Profile p3 = new Profile(icon3, "Donor C", "555-600-2020", "2222 Shattuck Ave", "We are donor C");

        mDonations.add(new DonationItem(p1, foodItems, "", "3pm"));
        mDonations.add(new DonationItem(p2, foodItems, "", "3pm"));
        mDonations.add(new DonationItem(p3, foodItems, "", "3pm"));

        setAdapterAndUpdateData();
    }
}
