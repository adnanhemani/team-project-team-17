package com.example.pantreasy;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.List;

public class Pantreasy extends Application {
    private Profile mProfile;
    private List<DonationItem> mDonationsRequested;
    private List<DonationItem> mDonationsPosted;
    public HashMap<String, Bitmap> mPictures;
    public HashMap<String, Profile> pantryProfiles;
    public HashMap<String, Profile> donorProfiles;

    public Profile getCurrentProfile() {
        return mProfile;
    }

    public void setCurrentProfile(Profile profile) {
        mProfile = profile;
    }

    public List<DonationItem> getPostedDonations() {
        return mDonationsPosted;
    }

    public void setPostedDonations(List<DonationItem> postedDonations) {
        mDonationsPosted = postedDonations;
    }

    public List<DonationItem> getRequestedDonations() {
        return mDonationsRequested;
    }

    public void setRequestedDonations(List<DonationItem> requestedDonations) {
        mDonationsRequested = requestedDonations;
    }
}
