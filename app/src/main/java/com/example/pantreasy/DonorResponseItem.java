package com.example.pantreasy;

import java.util.ArrayList;

public class DonorResponseItem {

    public String pantryProfileName;
    public String comment;
    public String UUID;
    public String donationUUID;
    public boolean confirmed;

    public DonorResponseItem(String pantryProfileName, String comment, String UUID, String donationUUID) {
        this.pantryProfileName = pantryProfileName;
        this.comment = comment;
        this.UUID = UUID;
        this.donationUUID = donationUUID;
        this.confirmed = false;
    }

}
