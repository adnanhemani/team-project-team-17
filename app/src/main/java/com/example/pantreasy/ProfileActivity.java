package com.example.pantreasy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton mHomeButton;
    private ImageButton mCameraButton;
    private Button mLogoutButton;
    private ImageView mProfilePicture;
    private ImageView mEditUsername;
    private ImageView mEditInfo;

    final int TAKE_PICTURE = 115;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView profile_name = findViewById(R.id.textView10);
        TextView phone = findViewById(R.id.textView11);
        TextView addr = findViewById(R.id.textView12);
        TextView bio = findViewById(R.id.textView13);
        ImageView profile_pic = findViewById(R.id.profile_picture);


        Profile this_profile = ((Pantreasy)getApplication()).getCurrentProfile();
        if (this_profile != null) {
            profile_name.setText(this_profile.name);
            phone.setText(this_profile.phoneNumber);
            addr.setText(this_profile.address);
            bio.setText(this_profile.description);
        }

        mHomeButton = findViewById(R.id.home_button);
        mCameraButton = findViewById(R.id.camera_button);
        mLogoutButton = findViewById(R.id.request_donation_button);
        mProfilePicture = findViewById(R.id.profile_picture);
        mEditUsername = findViewById(R.id.edit_username);
        mEditInfo = findViewById(R.id.edit_info);

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent back = new Intent(ProfileActivity.this, DonorHomeActivity.class);
                ProfileActivity.this.startActivity(back);
            }
        });

        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Set the image of the item you're adding
                capturePhoto();
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent logout = new Intent(ProfileActivity.this, Login.class);
                ProfileActivity.this.startActivity(logout);
            }
        });
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
                    mProfilePicture.setImageBitmap(bm);
                }
        }
    }
}
