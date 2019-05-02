package com.example.pantreasy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class DonorAddActivity extends AppCompatActivity {
    private ArrayList<FoodItem> mFoodList;
    private FoodItemAdapter mAdapter;
    private Button mAddToListButton;
    private Button mClearListButton;
    private TextView mFoodNameText;
    private TextView mExpirationDateText;
    private ImageView mBlurredBackgroundImage;
    private ImageView mFoodImage;
    private CardView mDonorAddPopupCard;
    private ImageButton mHomeButton;
    private ImageButton mCameraButton;
    private RecyclerView mRecyclerView;
    private ImageButton mCheckButton;
    private TextView mQuantityText;
    private RadioButton mPerishableButton;
    private CardView mRequestSentPopup;
    private Button mRequestSentConfirmButton;

    private ConstraintLayout mDonorAddPopup;
    private Button mPopupBackButton;
    private Button mPopupConfirmButton;
    private EditText mMonthInput;
    private EditText mDayInput;
    private EditText mHourInput;
    private EditText mMinuteInput;
    private AppCompatSpinner mPickupOptionSpinner;
    private RadioGroup mAmPmRadioGroup;
    private RadioButton mAmButton;
    private RadioButton mPmButton;


    final int TAKE_PICTURE = 115;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_add_view);

        mFoodList = new ArrayList<FoodItem>();
        ConstraintLayout layout = findViewById(R.id.donor_add_view);
        mFoodImage = layout.findViewById(R.id.main_image);
        mAddToListButton = layout.findViewById(R.id.add_to_list_button);
        mClearListButton = layout.findViewById(R.id.clear_list_button);
        mFoodNameText = layout.findViewById(R.id.food_name_text);
        mExpirationDateText = layout.findViewById(R.id.expiration_date_text);
        mBlurredBackgroundImage = layout.findViewById(R.id.blurred_background);
        mHomeButton = layout.findViewById(R.id.home_button);
        mCameraButton = layout.findViewById(R.id.camera_button);
        mRecyclerView = layout.findViewById(R.id.food_item_list);
        mCheckButton = layout.findViewById(R.id.check_button);
        mQuantityText = layout.findViewById(R.id.food_quantity_text);
        mPerishableButton = layout.findViewById(R.id.perishable_toggle);
        mRequestSentPopup = layout.findViewById(R.id.request_sent_popup);
        mRequestSentConfirmButton = mRequestSentPopup.findViewById(R.id.ok_button);

        mDonorAddPopupCard = layout.findViewById(R.id.popup_confirmation_layout);
        mDonorAddPopup = mDonorAddPopupCard.findViewById(R.id.interior_layout);
        mPopupBackButton = mDonorAddPopupCard.findViewById(R.id.back_button_popup);
        mPopupConfirmButton = mDonorAddPopupCard.findViewById(R.id.confirm_button);
        mMonthInput = mDonorAddPopupCard.findViewById(R.id.month_input);
        mDayInput = mDonorAddPopupCard.findViewById(R.id.day_input);
        mHourInput = mDonorAddPopupCard.findViewById(R.id.hour_input);
        mMinuteInput = mDonorAddPopupCard.findViewById(R.id.minute_input);
        mPickupOptionSpinner = mDonorAddPopupCard.findViewById(R.id.pickup_option_spinner);
        mAmPmRadioGroup = mDonorAddPopupCard.findViewById(R.id.ampm_radio_group);
        mAmButton = mDonorAddPopupCard.findViewById(R.id.am_button);
        mPmButton = mDonorAddPopupCard.findViewById(R.id.pm_button);


        mDonorAddPopupCard.setVisibility(View.GONE);
        mBlurredBackgroundImage.setVisibility(View.GONE);
        mRequestSentPopup.setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnClickForAddButton();
        setOnClickForClearButton();
        setOnClickForHomeButton();
        setOnClickForCameraButton();
        setOnClickForCheckButton();
        setOnClickForPopupRadioButtons();
        setOnClickForPopupBackButton();
        setOnClickForPopupConfirmButton();
        setOnClickForRequestSentConfirmButton();

        mFoodImage.setClipToOutline(true);

        setAdapterAndUpdateData();
    }

    private void setOnClickForAddButton() {
        mAddToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new FoodItem, add to list of FoodItems, update adapter
                String foodName = mFoodNameText.getText().toString();
                String expDate = mExpirationDateText.getText().toString();
                String quantity = mQuantityText.getText().toString();
                Boolean perishable = mPerishableButton.isChecked();
                FoodItem fi = new FoodItem(foodName, ((BitmapDrawable)mFoodImage.getDrawable()).getBitmap(), expDate, quantity, perishable);
                mFoodList.add(fi);
                setAdapterAndUpdateData();
            }
        });
    }

    private void setOnClickForClearButton() {
        mClearListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFoodList.clear();
                setAdapterAndUpdateData();
            }
        });

    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewDonation = new Intent(DonorAddActivity.this, DonorHomeActivity.class);
                DonorAddActivity.this.startActivity(addNewDonation);
            }
        });
    }

    private void setOnClickForCameraButton() {
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the image of the item you're adding
                capturePhoto();
            }
        });
    }

    private void setOnClickForCheckButton() {
        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Blur background and enable the confirmation popup
                ConstraintLayout view = (ConstraintLayout) findViewById(R.id.donor_add_view);
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                Bitmap bm = view.getDrawingCache();
                Bitmap blurBm = Utils.fastblur(bm, 1,60);
                mBlurredBackgroundImage.setImageBitmap(blurBm);
                mBlurredBackgroundImage.setVisibility(View.VISIBLE);
                mDonorAddPopupCard.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOnClickForPopupRadioButtons() {
        mAmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmButton.setTextColor(Color.BLACK);
                mPmButton.setTextColor(Color.LTGRAY);
            }
        });

        mPmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmButton.setTextColor(Color.LTGRAY);
                mPmButton.setTextColor(Color.BLACK);
            }
        });
    }

    private void setOnClickForPopupBackButton() {
        mPopupBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDonorAddPopupCard.setVisibility(View.GONE);
                mBlurredBackgroundImage.setVisibility(View.GONE);
            }
        });
    }

    private void setOnClickForPopupConfirmButton() {
        mPopupConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDonorAddPopupCard.setVisibility(View.GONE);
                mRequestSentPopup.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOnClickForRequestSentConfirmButton() {
        mRequestSentConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequestSentPopup.setVisibility(View.GONE);
                mBlurredBackgroundImage.setVisibility(View.GONE);
                // TODO
                // Must create a new donation and add it to the firebase database
                Intent viewResponses = new Intent(DonorAddActivity.this, DonorsPantryResponsesActivities.class);
                DonorAddActivity.this.startActivity(viewResponses);
            }
        });
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new FoodItemAdapter(this, this.mFoodList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bm = (Bitmap)data.getExtras().get("data");
                    mFoodImage.setImageBitmap(bm);
                }
        }
    }
}
