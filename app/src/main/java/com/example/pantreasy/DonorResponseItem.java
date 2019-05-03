package com.example.pantreasy;

import java.util.ArrayList;

public class DonorResponseItem {

    public String pantryProfileName;
    public String comment;
    public String UUID;

    public DonorResponseItem(String pantryProfileName, String comment, String UUID) {
        this.pantryProfileName = pantryProfileName;
        this.comment = comment;
        this.UUID = UUID;
    }

}
