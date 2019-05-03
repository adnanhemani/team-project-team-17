package com.example.pantreasy;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DonationItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<DonationItem> mDonations;

    public DonationItemAdapter(Context context, ArrayList<DonationItem> donationItems) {
        mContext = context;
        mDonations = donationItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // here, we specify what kind of view each cell should have. In our case, all of them will have a view
        View view = LayoutInflater.from(mContext).inflate(R.layout.pantry_donate_item, parent, false);
        return new DonationItemViewHolder(view);
    }


    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DonationItem donationItem = mDonations.get(position);
        ((DonationItemViewHolder) holder).bind(donationItem);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDonations.size();
    }

    class DonationItemViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ConstraintLayout mDonationItemLayout;
        public TextView mDonorName;
        public ImageView mDonorImage;
        public TextView mTime;
        public TextView mItemList;
        public TextView mDistance;
        public View mItemView;

        public DonationItemViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mDonationItemLayout = itemView.findViewById(R.id.donation_item);
            mDonorName = mDonationItemLayout.findViewById(R.id.donor_name);
            mDonorImage = mDonationItemLayout.findViewById(R.id.donor_image);
            mTime = mDonationItemLayout.findViewById(R.id.pickup_dropoff_time);
            mItemList = mDonationItemLayout.findViewById(R.id.food_items_list);
            mDistance = mDonationItemLayout.findViewById(R.id.distance_text);
        }

        void bind(DonationItem donationItem) {

            //mDonorName.setText(donationItem.profile.name);
            //mDonorImage.setImageBitmap(donationItem.profile.imageBitmap);
            mTime.setText(donationItem.time);
            mDistance.setText("1000mi");
            mItemList.setText(donationItem.foodListAsString());

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goToSecondActivityIntent = new Intent(mContext, PantryViewDonation.class);
                    mContext.startActivity(goToSecondActivityIntent);
                }
            });
        }
    }
}

