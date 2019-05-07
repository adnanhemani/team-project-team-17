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
import java.util.List;

public class DatabaseUpdates extends JobIntentService {
    public String profileName;
    private Profile mProfile;

    public FirebaseManager mFirebaseManager;
    private FirebaseAuth mAuth;

    public ValueEventListener mProfileListener;
    public ValueEventListener mRequestedDonationListener;
    public ValueEventListener mPostedDonationListener;
    public ValueEventListener mPantryProfileListener;
    public ValueEventListener mDonorProfileListener;

    // Temporary versions of the globals in Pantreasy
    private List<DonationItem> mDonationsRequested;
    private List<DonationItem> mDonationsPosted;
    private List<DonationItem> mDonations;
    public ArrayList<ImageGetter> mImageGetters;

    public HashMap<String, Bitmap> mPictures;
    public HashMap<String, Profile> pantryProfiles;
    public HashMap<String, Profile> donorProfiles;

    private int numPantryProfiles;
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        init(intent.getStringExtra("profileName"));
    }

    private void pullData() {
        mDonationsRequested = new ArrayList<>();
        mDonationsPosted = new ArrayList<>();
        mImageGetters = new ArrayList<>();
        mDonations = new ArrayList<>();

        mPictures = new HashMap<>();
        pantryProfiles = new HashMap<>();
        donorProfiles = new HashMap<>();


        numPantryProfiles = 0;

        mFirebaseManager.getProfile(profileName, mProfileListener);
        mFirebaseManager.getDonations(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDonations = mFirebaseManager.donationsFromSnapshot(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){}
        });
    }

    private void setDonorProfileListener() {
        mDonorProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p = mFirebaseManager.getProfileFromDataSnapshot(dataSnapshot);
                donorProfiles.put(p.name, p);

                getImagesIfDone();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void setPantryProfileListener() {
        mPantryProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p = mFirebaseManager.getProfileFromDataSnapshot(dataSnapshot);
                pantryProfiles.put(p.name, p);

                getImagesIfDone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void setPostedDonationListener() {
        mPostedDonationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DonationItem d = mFirebaseManager.getDonationFromDataSnapshot(dataSnapshot);
                for (int i = 0; i < d.responseItems.size(); i++) {
                    numPantryProfiles++;
                }
                mDonationsPosted.add(d);

                getPantryProfilesIfDoneGettingDonationRequests();
                getImagesIfDone();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void getPantryProfilesIfDoneGettingDonationRequests() {
        if (mDonationsPosted.size() == mProfile.postedDonationUUIDs.size()) {
            for (int i = 0; i < mDonationsPosted.size(); i++) {
                DonationItem d = mDonationsPosted.get(i);
                for (int j = 0; j < d.responseItems.size(); j++) {
                    mFirebaseManager.getProfile(d.responseItems.get(j).pantryProfileName, mPantryProfileListener);
                }
            }
        }
    }

    private void setRequestedDonationListener() {
        mRequestedDonationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DonationItem d = mFirebaseManager.getDonationFromDataSnapshot(dataSnapshot);
                mDonationsRequested.add(d);
                mFirebaseManager.getProfile(d.profileName, mDonorProfileListener);

                getImagesIfDone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void setProfileListener() {
        mProfileListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProfile = mFirebaseManager.getProfileFromDataSnapshot(dataSnapshot);
                mDonationsRequested = new ArrayList<DonationItem>();
                mDonationsPosted = new ArrayList<DonationItem>();
                for (int i = 0; i < mProfile.requestedDonationUUIDs.size(); i++) {
                    mFirebaseManager.getDonation(mProfile.requestedDonationUUIDs.get(i), mRequestedDonationListener);
                }
                for (int i = 0; i < mProfile.postedDonationUUIDs.size(); i++) {
                    mFirebaseManager.getDonation(mProfile.postedDonationUUIDs.get(i), mPostedDonationListener);
                }
                getImagesIfDone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
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

    private void init(String profileName) {
        this.profileName = profileName;
        mFirebaseManager = new FirebaseManager(this);
        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();

        setProfileListener();
        setRequestedDonationListener();
        setPostedDonationListener();
        setPantryProfileListener();
        setDonorProfileListener();
        pullData();
    }

    private class ImageGetter {
        private OnSuccessListener<byte[]> mImageSuccessListener;
        private String mImageName;
        public ImageGetter(final String imageName) {
            mImageName = imageName;
            mImageSuccessListener = new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] data) {
                    mPictures.put(mImageName, mFirebaseManager.bitmapFromBytes(data));
                    updateGlobalsIfDone();
                }
            };
            mFirebaseManager.imageFromStorage(mImageName, mImageSuccessListener);
        }
    }

    void updateGlobalsIfDone() {
        int numRequested = (mProfile.requestedDonationUUIDs != null) ? mProfile.requestedDonationUUIDs.size() : 0;
        if (mPictures.size() != numPantryProfiles + numRequested + 1) return;

        ((Pantreasy) this.getApplication()).setCurrentProfile(mProfile);
        ((Pantreasy) this.getApplication()).setPostedDonations(mDonationsPosted);
        ((Pantreasy) this.getApplication()).setRequestedDonations(mDonationsRequested);
        ((Pantreasy) this.getApplication()).donorProfiles = new HashMap<>(donorProfiles);
        ((Pantreasy) this.getApplication()).pantryProfiles = new HashMap<>(pantryProfiles);
        ((Pantreasy) this.getApplication()).mPictures = new HashMap<>(mPictures);
    }

    void getImagesIfDone() {
        if (mProfile.postedDonationUUIDs.size() != mDonationsPosted.size()
            || mProfile.requestedDonationUUIDs.size() != mDonationsRequested.size()
            || pantryProfiles.size() != numPantryProfiles
            || donorProfiles.size() != mDonationsRequested.size())
            return;
        mImageGetters.add(new ImageGetter(mProfile.imageName));
        for (String s : donorProfiles.keySet()) {
            Profile p = donorProfiles.get(s);
            mImageGetters.add(new ImageGetter(p.imageName));
        }
        for (String s : pantryProfiles.keySet()) {
            Profile p = pantryProfiles.get(s);
            mImageGetters.add(new ImageGetter(p.imageName));
        }
    }
}
