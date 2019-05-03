package com.example.pantreasy;

import java.util.ArrayList;

public class DonorResponseItem {

    public Profile pantryProfile;
    public String comment;
    public String UUID;

    public DonorResponseItem(Profile pantryProfile, String comment, String UUID) {
        this.pantryProfile = pantryProfile;
        this.comment = comment;
        this.UUID = UUID;
    }

}
