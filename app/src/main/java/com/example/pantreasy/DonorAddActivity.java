package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class DonorAddActivity extends AppCompatActivity {
    private ArrayList<FoodItem> mFoodList;
    private Button addToList;
    private Button clearList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_add);
        ConstraintLayout layout = findViewById(R.id.donor_add);
        ImageView foodImage = layout.findViewById(R.id.main_image);
        addToList = layout.findViewById(R.id.add_to_list_button);
        clearList = layout.findViewById(R.id.clear_list_button);
        setOnClickForAddButton();
        setOnClickForClearButton();
        foodImage.setClipToOutline(true);
    }

    private void setOnClickForAddButton() {
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setOnClickForClearButton() {
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
