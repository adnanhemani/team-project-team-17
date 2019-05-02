package com.example.pantreasy;

import android.graphics.Bitmap;

public class Profile {
    public Bitmap imageBitmap;
    public String name;
    public String phoneNumber;
    public String address;
    public String description;

    public Profile(Bitmap imageBitmap, String name, String phoneNumber, String address, String description) {
        this.imageBitmap = imageBitmap;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
    }
}
