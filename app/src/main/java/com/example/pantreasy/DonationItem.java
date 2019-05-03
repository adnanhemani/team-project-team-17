package com.example.pantreasy;

import java.util.ArrayList;

public class DonationItem {
    public Profile profile;
    public ArrayList<FoodItem> foodItems;
    public String comment;
    public String time;

    public DonationItem(Profile profile, ArrayList<FoodItem> foodItems, String comment, String time) {
        this.profile = profile;
        this.foodItems = foodItems;
        this.comment = comment;
        this.time = time;
    }

    public String foodListAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Food Items: ");
        for (int i = 0; i < foodItems.size(); i++) {
            sb.append(foodItems.get(i).name);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
