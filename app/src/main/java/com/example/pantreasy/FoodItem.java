package com.example.pantreasy;

import java.util.Date;

public class FoodItem {
    public String name;
    public Date expirationDate;
    public String unitOfMeasurement;
    float quantity;

    public FoodItem(String name, Date expDate, String unitOfMeasurement, float quanitity) {
        this.name = name;
        this.expirationDate = expDate;
        this.unitOfMeasurement= unitOfMeasurement;
        this.quantity = quanitity;
    }
}
