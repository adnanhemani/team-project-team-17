package com.example.pantreasy;

import java.util.ArrayList;

public class DonorResponseItem {

    public String pantryProfileName;
    public String comment;
    public String UUID;
    public String donationUUID;
    public int confirmed;

    public DonorResponseItem(String pantryProfileName, String comment, String UUID, String donationUUID, int confirmed) {
        this.pantryProfileName = pantryProfileName;
        this.comment = comment;
        this.UUID = UUID;
        this.donationUUID = donationUUID;
        this.confirmed = confirmed;
    }

}
