package com.example.cs160_sp18.prog3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.location.Location.distanceBetween;
import static android.support.v4.app.ActivityCompat.requestPermissions;

public class LandmarkAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Landmarks> mLandmarks;

    public LandmarkAdapter(Context context, ArrayList<Landmarks> landmarks) {
        mContext = context;
        mLandmarks = landmarks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // here, we specify what kind of view each cell should have. I
        // n our case, all of them will have a view
        // made from comment_cell_layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.landmark_cell_layout, parent, false);
        return new LandmarkViewHolder(view);
    }


    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // here, we the comment that should be displayed at index `position` in our recylcer view
        // everytime the recycler view is refreshed, this method is called getItemCount() times (because
        // it needs to recreate every cell).
        Landmarks landmark = mLandmarks.get(position);
        ((LandmarkViewHolder) holder).bind(landmark);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mLandmarks.size();
    }

}

class LandmarkViewHolder extends RecyclerView.ViewHolder {

    // each data item is just a string in this case
    public ConstraintLayout mCommentBubbleLayout;
    public TextView name;
    public TextView distance;
    public ImageView photo;
    public View inst;
    public int distance_away;

    public LandmarkViewHolder(View itemView) {
        super(itemView);
        inst = itemView;
        mCommentBubbleLayout = itemView.findViewById(R.id.landmark_cell_layout);
        name = mCommentBubbleLayout.findViewById(R.id.landmark_name);
        distance = mCommentBubbleLayout.findViewById(R.id.landmark_distance);
        photo = mCommentBubbleLayout.findViewById(R.id.landmark_photo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (distance_away <= 10) {
                    // Open new intent
                    System.out.println("open chatroom");
                    Intent goToSecondActivityIntent = new Intent(inst.getContext(), CommentFeedActivity.class);
                    goToSecondActivityIntent.putExtra("username", BearView.username);
                    goToSecondActivityIntent.putExtra("chatroom", name.getText());
                    inst.getContext().startActivity(goToSecondActivityIntent);
                } else {
                    Toast.makeText(inst.getContext(), "You must be within a 10 meter radius to open chatroom.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    void bind(Landmarks landmark) {
        name.setText(landmark.name);
        int resID = inst.getContext().getResources().getIdentifier(landmark.file_name, "drawable", inst.getContext().getPackageName());
        photo.setImageResource(resID);
        LocationManager lm = (LocationManager) inst.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(inst.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(inst.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] REQUEST_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions((Activity)inst.getContext(), REQUEST_PERMS, 1337);
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        float[] results = new float[1];
        distanceBetween(latitude, longitude, landmark.coordinates.first, landmark.coordinates.second, results);
        distance_away = (int) results[0];
        distance.setText(distance_away + " meters away");
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
        @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
        @Override public void onProviderEnabled(String provider) { }
        @Override public void onProviderDisabled(String provider) { }
    };
}
