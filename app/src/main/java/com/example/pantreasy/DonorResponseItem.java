package com.example.pantreasy;

public class DonorResponseItem {

    public String pantryProfileName;
    public String comment;
    public String UUID;
    public String donationUUID;
    public int confirmed;
    public String indexesOfitemsToTake;

    public DonorResponseItem(String pantryProfileName, String comment, String UUID, String donationUUID, int confirmed, String indexesOfItemsToTake) {
        this.pantryProfileName = pantryProfileName;
        this.comment = comment;
        this.UUID = UUID;
        this.donationUUID = donationUUID;
        this.confirmed = confirmed;
        this.indexesOfitemsToTake = indexesOfItemsToTake;
    }

}
