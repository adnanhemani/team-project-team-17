package com.example.pantreasy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import java.util.ArrayList;

public class PantryViewDonationsActivity extends AppCompatActivity {
    private ArrayList<DonorResponseItem> mResponseItems;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mLayout;
    private DonorResponseItemAdapter mAdapter;
    private ImageButton mHomeButton;
    private ImageButton mConfirmButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_donate_list_view);

    }
}
