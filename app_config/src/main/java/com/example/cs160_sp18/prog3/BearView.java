package com.example.cs160_sp18.prog3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BearView extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<Landmarks> landmarks = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear_view);
        Toolbar toolbar = findViewById(R.id.bear_toolbar);
        toolbar.setTitleMarginStart(50);
        toolbar.setTitle("Landmarks");
        this.getSupportActionBar().hide();
        mRecyclerView = (RecyclerView)findViewById(R.id.statue_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        makeLandmarks();

        setAdapterAndUpdateData();

        Intent goToSecondActivityIntent = getIntent();
        Bundle intentExtras = goToSecondActivityIntent.getExtras();
        if(intentExtras!=null) {
            username =(String) intentExtras.get("username");
        }

        Button refresh_location = findViewById(R.id.refresh_location);
        refresh_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Refresh Location");
                setAdapterAndUpdateData();
            }
        });
    }


    private void makeLandmarks() {
        String json_str = null;
        try {
            InputStream is = this.getAssets().open("bear_statues.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json_str = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(json_str);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String coordinates = (String) jsonObject.get("coordinates");
                String[] coordinates_split = coordinates.split(", ");
                Landmarks new_landmark = new Landmarks((String) jsonObject.get("landmark_name"), (String) jsonObject.get("filename"),
                        new Pair(Double.parseDouble(coordinates_split[0]), Double.parseDouble(coordinates_split[1])));
                landmarks.add(new_landmark);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new LandmarkAdapter(this, landmarks);
        mRecyclerView.setAdapter(mAdapter);

    }
}
