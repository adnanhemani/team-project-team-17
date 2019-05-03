package com.example.pantreasy;

import java.util.List;

public class DonationItem {
    public String profileName;
    public List<FoodItem> foodItems;
    public List<DonorResponseItem> responseItems;
    public String comment;
    public String time;
    public boolean pickup;
    public int confirmed;
    public String UUID;

    public DonationItem(String profileName, List<FoodItem> foodItems, List<DonorResponseItem> responseItems, String time, boolean pickup, String UUID) {
        this.profileName = profileName;
        this.foodItems = foodItems;
        this.responseItems = responseItems;
        this.time = time;
        this.pickup = pickup;
        this.confirmed = 0;
        this.UUID = UUID;
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
