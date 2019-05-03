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

public class PantryViewResponsesActivity extends AppCompatActivity {

    private ArrayList<DonationItem> mDonations;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private PantryResponseItemAdapter mAdapter;
    private ImageButton mHomeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_list_view);
        mDonations = new ArrayList<DonationItem>();
        mLayout = findViewById(R.id.pantry_response_list);

        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);

        setOnClickForHomeButton();

        generateDonationItems();
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

    public void generateDonationItems() {
        ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
        foodItems.add(new FoodItem("Eggs", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Butter", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Cheese", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Noodles", null, "2/2/22", "5", true));
        foodItems.add(new FoodItem("Beef", null, "2/2/22", "5", true));


        Bitmap icon1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_a_profile_pic);
        Profile p1 = new Profile("PLACEHOLDER", "Donor A", "555-420-1337", "1777 Hearst Ave", "We are donor A", null, null);

        Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_b_profile_pic);
        Profile p2 = new Profile("PLACEHOLDER", "Donor B", "555-555-5555", "1777 Le Roy Ave", "We are donor B", null, null);
        Bitmap icon3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donor_c_profile_pic);
        Profile p3 = new Profile("PLACEHOLDER", "Donor C", "555-600-2020", "2222 Shattuck Ave", "We are donor C",null, null);
        //DonationItem d = new DonationItem(p1, foodItems, "", "3pm", true);
        //d.confirmed = -1;
        //mDonations.add(d);
        //d = new DonationItem(p2, foodItems, "", "3pm", true);
        //d.confirmed = 0;
        //mDonations.add(d);
        //d = new DonationItem(p3, foodItems, "", "3pm", true);
        //d.confirmed = 1;
        //mDonations.add(d);

        setAdapterAndUpdateData();
    }
}
