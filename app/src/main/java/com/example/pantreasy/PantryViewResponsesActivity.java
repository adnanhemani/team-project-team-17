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
import com.google.firebase.database.core.ValueEventRegistration;

import java.util.ArrayList;
import java.util.List;

public class PantryViewResponsesActivity extends AppCompatActivity {

    private List<DonationItem> mDonations;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private PantryResponseItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private BroadcastReceiver mReceiver;
    private ImageButton mRefreshButton;
    private FirebaseManager fm = new FirebaseManager(PantryViewResponsesActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_list_view);

        mDonations = new ArrayList<>();
        mLayout = findViewById(R.id.pantry_response_list);
        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);
        mRefreshButton = mLayout.findViewById(R.id.refreshButton);
        final List<DonationItem> dataForAdapter = new ArrayList<DonationItem>();
        ((Pantreasy)getApplication()).getCurrentProfile();
        for (String uuid: ((Pantreasy)getApplication()).getCurrentProfile().requestedDonationUUIDs) {
            fm.getDonation(uuid, new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataForAdapter.add(fm.getDonationFromDataSnapshot(dataSnapshot));
                    ((Pantreasy)getApplication()).setRequestedDonations(dataForAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        setOnClickForHomeButton();
        setOnClickForRefreshButton();
        setAdapterAndUpdateData();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setAdapterAndUpdateData();
            }
        };
        getApplication().registerReceiver(mReceiver, new IntentFilter(((Pantreasy)getApplicationContext()).USER_DATA_FILTER));
        Utils.updateGlobals(this, "Berkeley Food Pantry");
    }

    private void setOnClickForRefreshButton() {
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.updateGlobals(PantryViewResponsesActivity.this, "Berkeley Food Pantry");
            }
        });
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
        if (((Pantreasy)getApplication()).getRequestedDonations() == null) return;
        mAdapter = new PantryResponseItemAdapter(this, ((Pantreasy)getApplication()).getRequestedDonations());
        mRecyclerView.setAdapter(mAdapter);
    }
}
