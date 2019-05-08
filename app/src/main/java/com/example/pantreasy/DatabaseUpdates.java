package com.example.pantreasy;

import android.app.IntentService;
import android.content.Intent;

public class DatabaseUpdates extends IntentService {
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        // Do work here, based on the contents of dataString
    }
    public String UUID;
    public DatabaseUpdates(String name, String UUID) {
        super(name);
        this.UUID = UUID;
    }
}
