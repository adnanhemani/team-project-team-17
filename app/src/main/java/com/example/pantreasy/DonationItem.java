package com.example.pantreasy;

import java.util.ArrayList;
import java.util.List;

public class DonationItem {
    public String profileName;
    public List<FoodItem> foodItems;
    public List<DonorResponseItem> responseItems;
    public String time;
    public boolean pickup;
    public String UUID;
    public String itemsTaken;

    public DonationItem(String profileName, List<FoodItem> foodItems, List<DonorResponseItem> responseItems, String time, boolean pickup, String UUID, String itemsTaken) {
        this.profileName = profileName;
        this.foodItems = foodItems;
        this.responseItems = responseItems;
        this.time = time;
        this.pickup = pickup;
        this.UUID = UUID;
        this.itemsTaken = itemsTaken;
    }

    public String foodListAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Food Items: ");
        List<FoodItem> availableFoodItems = getAvailableFoodItems();
        for (int i = 0; i < availableFoodItems.size(); i++) {
            sb.append(availableFoodItems.get(i).name);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public List<FoodItem> getAvailableFoodItems() {
        List<FoodItem> result = new ArrayList<>();
        boolean[] temp = new boolean[foodItems.size()];
        String[] indexes = itemsTaken.split(" ");
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i].equals("")) continue;
            int j = Integer.parseInt(indexes[i]);
            temp[j] = true;
        }
        for (int i = 0; i < foodItems.size(); i++) {
            if (!temp[i])
                result.add(foodItems.get(i));
        }
        return result;
    }

    public List<FoodItem> getTakenFoodItems(String takenItems) {
        List<FoodItem> result = new ArrayList<>();
        boolean[] temp = new boolean[foodItems.size()];
        String[] indexes = takenItems.split(" ");
        for (int i = 0; i < indexes.length; i++) {
            int j = Integer.parseInt(indexes[i]);
            temp[j] = true;
        }
        for (int i = 0; i < foodItems.size(); i++) {
            if (temp[i] == false)
                result.add(foodItems.get(i));
        }
        return result;
    }
}
