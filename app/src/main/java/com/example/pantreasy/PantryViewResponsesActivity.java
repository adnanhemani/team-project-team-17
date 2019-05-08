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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_response_list_view);

        mDonations = new ArrayList<>();
        mLayout = findViewById(R.id.pantry_response_list);
        mRecyclerView = mLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeButton = mLayout.findViewById(R.id.home_button);

        setOnClickForHomeButton();
        setAdapterAndUpdateData();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setAdapterAndUpdateData();
            }
        };
        getApplication().registerReceiver(mReceiver, new IntentFilter(((Pantreasy)getApplicationContext()).USER_DATA_FILTER));

        Utils.updateGlobals(this, ((Pantreasy)getApplication()).getCurrentProfile().name);
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
