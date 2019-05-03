package com.example.pantreasy;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void getDonations(ValueEventListener valueEventListener) {
        mDonationItems.addListenerForSingleValueEvent(valueEventListener);
    }

    public DonationItem getDonationFromDataSnapshot(DataSnapshot dataSnapshot) {
        HashMap<String, Object> h = (HashMap<String, Object>) dataSnapshot.getValue();
        String UUID = (String) h.get("UUID");
        int confirmed = ((Long)h.get("confirmed")).intValue();
        ArrayList<HashMap> foodItemData = (ArrayList<HashMap>) h.get("foodItems");
        List foodItems = foodItemsFromArrayList (foodItemData);
        boolean pickup = (boolean) h.get("pickup");
        String profileName = (String) h.get("profileName");
        String time = (String) h.get("time");
        // Add Response Items
        return new DonationItem(profileName, foodItems, null, time, pickup, UUID);
    }

    public List<DonationItem> donationsFromSnapshot(DataSnapshot dataSnapshot) {
        List<DonationItem> result = new ArrayList<DonationItem>();
        HashMap data = (HashMap)dataSnapshot.getValue();
        for (Object s : data.keySet()) {
            HashMap<String, Object> donation = (HashMap<String, Object>) data.get(s);
            String UUID = (String) donation.get("UUID");
            int confirmed = ((Long)donation.get("confirmed")).intValue();
            ArrayList<HashMap> foodItemData = (ArrayList<HashMap>) donation.get("foodItems");
            List foodItems = foodItemsFromArrayList (foodItemData);
            boolean pickup = (boolean) donation.get("pickup");
            String profileName = (String) donation.get("profileName");
            String time = (String) donation.get("time");
            // Add Response Items
            result.add(new DonationItem(profileName, foodItems, null, time, pickup, UUID));
        }
        return result;
    }

    private List<FoodItem> foodItemsFromArrayList (ArrayList<HashMap> foodItemData) {
        List<FoodItem> foodItems = new ArrayList<FoodItem>();
        for (int i = 0; i < foodItemData.size(); i++) {
            HashMap foodItem = foodItemData.get(i);
            boolean perishable = (boolean)foodItem.get("perishable");
            String imageName = (String)foodItem.get("imageName");
            String quantity = (String)foodItem.get("quantity");
            String name = (String)foodItem.get("name");
            String expiration = (String)foodItem.get("expirationDate");
            foodItems.add(new FoodItem(name, imageName, expiration, quantity, perishable));
        }
        return foodItems;
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
