package com.example.pantreasy;

import java.util.Date;

public class FoodItem {
    public String name;
    public Date expirationDate;
    public String unitOfMeasurement;
    public boolean perishable;
    float quantity;

    public FoodItem(String name, Date expDate, String unitOfMeasurement, float quanitity, boolean perishable) {
        this.name = name;
        this.expirationDate = expDate;
        this.unitOfMeasurement= unitOfMeasurement;
        this.quantity = quanitity;
        this.perishable = perishable;
    }
}
