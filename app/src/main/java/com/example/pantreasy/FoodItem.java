package com.example.pantreasy;

import android.graphics.Bitmap;

import java.util.Date;

public class FoodItem {
    public String imageName;
    public String name;
    public String expirationDate;
    public boolean perishable;
    public String quantity;

    public FoodItem(String name, String imageName, String expDate, String quanitity, boolean perishable) {
        this.name = name;
        this.expirationDate = expDate;
        this.quantity = quanitity;
        this.perishable = perishable;
        this.imageName = imageName;
    }
}
