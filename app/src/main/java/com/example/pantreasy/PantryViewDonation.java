package com.example.pantreasy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class PantryViewDonation extends AppCompatActivity {
    private ConstraintLayout mLayout;
    private TextView mDonorName;
    private RecyclerView mRecyclerView;
    private TextView mPickupOrDropoffText;
    private TextView mDateTime;
    private TextView mAddress;
    private TextView mConfirmedText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_view);

        mLayout = findViewById(R.id.pantry_view_donation);
        mDonorName = mLayout.findViewById(R.id.donor_name_pantry_view_donation);
        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mPickupOrDropoffText = mLayout.findViewById(R.id.pickup_dropoff_text2);
        mDateTime = mLayout.findViewById(R.id.date_time_text);
        mAddress = mLayout.findViewById(R.id.address_text);
        mConfirmedText = mLayout.findViewById(R.id.confirmed_text);



    }
}
