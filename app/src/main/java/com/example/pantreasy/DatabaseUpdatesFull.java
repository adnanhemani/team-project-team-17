package com.example.pantreasy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseUpdatesFull extends JobIntentService {
    public FirebaseManager mFirebaseManager;
    private FirebaseAuth mAuth;

    public ValueEventListener mAllDonationListener;
    public ValueEventListener mAllProfileListener;


    // Temporary versions of the globals in Pantreasy
    private List<DonationItem> mDonations;
    private HashMap<String, Profile> mProfiles;
    public ArrayList<ImageGetter> mImageGetters;

    public HashMap<String, Bitmap> mAllPictures;

    private Set<String> foodItemSet;
    private Set<String> donationProfileSet;
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        getAllDonationsAndProfiles();
    }

    private void getAllDonationsAndProfiles() {
        Pantreasy p = ((Pantreasy) getApplication());
        mAllPictures = new HashMap<>();
        this.donationProfileSet = new HashSet<>();
        mFirebaseManager = new FirebaseManager(this);
        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();
        mImageGetters = new ArrayList<>();
        foodItemSet = new HashSet<>();
        setAllDonationListener();
        setAllProfileListener();
        mProfiles = new HashMap<>();
        mDonations = new ArrayList<>();
        mFirebaseManager.getDonations(mAllDonationListener);
    }

    private void setAllDonationListener() {
        mAllDonationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDonations = mFirebaseManager.donationsFromSnapshot(dataSnapshot);
                for (int i = 0; i < mDonations.size(); i++) {
                    DonationItem d = mDonations.get(i);
                    String profileName = d.profileName;
                    mFirebaseManager.getProfile(profileName, mAllProfileListener);
                    donationProfileSet.add(profileName);
                    for (FoodItem f : d.foodItems) {
                        foodItemSet.add(f.imageName);
                    }
                    for (FoodItem f : d.foodItems) {
                        mImageGetters.add(new ImageGetter(f.imageName));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){}
        };
    }

    private void setAllProfileListener() {
        mAllProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p = mFirebaseManager.getProfileFromDataSnapshot(dataSnapshot);
                mProfiles.put(p.name, p);
                mImageGetters.add(new ImageGetter(p.imageName));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }

    private void updateGlobalDonationsAndProfiles() {
        if (mAllPictures.size() != foodItemSet.size() + mProfiles.size()) return;
        ((Pantreasy) this.getApplication()).allProfiles = new HashMap<>(mProfiles);
        ((Pantreasy) this.getApplication()).allDonations = new ArrayList<>(mDonations);
        ((Pantreasy) this.getApplication()).mAllPictures = new HashMap<>(mAllPictures);
        Pantreasy p = ((Pantreasy)getApplicationContext());
        sendBroadcast(new Intent(((Pantreasy)getApplicationContext()).ALL_DONATIONS_AND_PROFILES_FILTER));
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }

    private class ImageGetter {
        private OnSuccessListener<byte[]> mImageSuccessListener;
        private String mImageName;
        public ImageGetter(final String imageName) {
            mImageName = imageName;
            mImageSuccessListener = new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] data) {
                    mAllPictures.put(mImageName, mFirebaseManager.bitmapFromBytes(data));
                    updateGlobalDonationsAndProfiles();
                }
            };
            mFirebaseManager.imageFromStorage(mImageName, mImageSuccessListener);
        }
    }
}
