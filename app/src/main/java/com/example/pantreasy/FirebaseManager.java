package com.example.pantreasy;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FirebaseManager {
    private FirebaseDatabase database;
    private DatabaseReference mProfiles;
    private DatabaseReference mDonationItems;
    private DatabaseReference mResponseItems;
    private DatabaseReference mFoodItems;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;

    public FirebaseManager(AppCompatActivity activity) {
        FirebaseApp.initializeApp(activity);
        database = FirebaseDatabase.getInstance();
        mProfiles = database.getReference("Profiles");
        mDonationItems = database.getReference("Donations");
        mFoodItems = database.getReference("Food");
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReferenceFromUrl("gs://pantreasy.appspot.com");
    }

    public void addProfile(Bitmap bm, Profile profile) {
        uploadBitmap(bm, profile.imageName);
        mProfiles.child(profile.name).setValue(profile);
    }

    public void addDonation(String profileName, DonationItem donation) {
        mProfiles.child(profileName).child("postedDonationUUIDs").child(donation.UUID).setValue(donation.UUID);
        mDonationItems.child(donation.UUID).setValue(donation);
    }

    public void addResponse(String profileName, String donationUUID, DonorResponseItem responseItem) {
        mProfiles.child(profileName).child("requestedDonationUUIDs").child(donationUUID).setValue(donationUUID);
        mDonationItems.child(donationUUID).child("responseItems").child(responseItem.UUID).setValue(responseItem);
    }

    public void getProfile(String profileName, ValueEventListener valueEventListener) {
        mProfiles.child(profileName).addListenerForSingleValueEvent(valueEventListener);
    }

    public void getDonation(String donationUUID, ValueEventListener valueEventListener) {
        mDonationItems.child(donationUUID).addListenerForSingleValueEvent(valueEventListener);
    }

    public void getDonationUUIDs(String profileName, ValueEventListener valueEventListener) {
        mProfiles.child("postedDonationUUIDs").addListenerForSingleValueEvent(valueEventListener);
    }

    public void getRequestUUIDs(String profileName, ValueEventListener valueEventListener) {
        mProfiles.child("requestedDonationUUIDs").addListenerForSingleValueEvent(valueEventListener);
    }

    public void getResponses(String donationUUID, ValueEventListener valueEventListener) {
        mDonationItems.child(donationUUID).child("responseItems");
    }



    public void uploadBitmap(Bitmap bm, String name) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageRef.child(name).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                return;
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            }
        });
    }
}
