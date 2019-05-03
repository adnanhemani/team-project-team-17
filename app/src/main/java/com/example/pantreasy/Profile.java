package com.example.pantreasy;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    public String imageName;
    public String name;
    public String phoneNumber;
    public String address;
    public String description;

    public List<String> postedDonationUUIDs;
    public List<String> requestedDonationUUIDs;

    public Profile(String imageName, String name, String phoneNumber, String address, String description, List<String> postedDonationUUIDs, List<String> requestedDonationUUIDs) {
        this.imageName = imageName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;

        this.postedDonationUUIDs = postedDonationUUIDs;
        this.requestedDonationUUIDs = requestedDonationUUIDs;
    }
}
