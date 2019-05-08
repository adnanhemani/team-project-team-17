package com.example.pantreasy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PantryViewDonationsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private DonationItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_donate_list_view);
        mLayout = findViewById(R.id.donor_response_view);
        mRecyclerView = mLayout.findViewById(R.id.donation_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);

        setOnClickForHomeButton();
        setAdapterAndUpdateData();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Pantreasy p = ((Pantreasy) getApplication());
                setAdapterAndUpdateData();
            }
        };
        getApplication().registerReceiver(mReceiver, new IntentFilter(((Pantreasy)getApplicationContext()).ALL_DONATIONS_AND_PROFILES_FILTER));

        Utils.updateListOfAllDonationsAndProfiles(this);
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

    private void setAdapterAndUpdateData() {
        if (((Pantreasy)getApplication()).allDonations == null)
            return;
        mAdapter = new DonationItemAdapter(this, ((Pantreasy)getApplication()).allDonations);
        mRecyclerView.setAdapter(mAdapter);
    }
}
