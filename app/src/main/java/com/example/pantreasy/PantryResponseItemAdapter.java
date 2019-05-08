package com.example.pantreasy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PantryResponseItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<DonationItem> mDonations;

    public PantryResponseItemAdapter(Context context, List<DonationItem> donationItems) {
        mContext = context;
        mDonations = donationItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pantry_response_item, parent, false);
        return new DonationItemViewHolder(view);
    }

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
        public View mItemView;
        public TextView mDonorName;
        public ImageView mDonorImage;
        public TextView mPhoneNumberText;
        public TextView mTimeText;
        public TextView mConfirmationText;
        public ValueEventListener mProfileListener;
        public Profile mProfile;
        public DonorResponseItem mResponseItem;

        public DonationItemViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mDonationItemLayout = mItemView.findViewById(R.id.donation_item);
            mDonorName = mDonationItemLayout.findViewById(R.id.donor_name);
            mDonorImage = mDonationItemLayout.findViewById(R.id.donor_image);
            mPhoneNumberText = mDonationItemLayout.findViewById(R.id.phone_number_text);
            mTimeText = mDonationItemLayout.findViewById(R.id.time_text);
            mConfirmationText = mDonationItemLayout.findViewById(R.id.confirmed_text);
            mProfile = ((Pantreasy)mContext.getApplicationContext()).getCurrentProfile();
        }

        void bind(final DonationItem donationItem) {
            mDonorName.setText(donationItem.profileName);
            mPhoneNumberText.setText(donationItem.time);
            if (donationItem.pickup)
                mTimeText.setText("Pick-Up: " + donationItem.time);
            else
                mTimeText.setText("Drop-Off: " + donationItem.time);
            if (donationItem.responseItems. < 0) {
                mConfirmationText.setText("Rejected");
                mConfirmationText.setBackgroundColor(Color.RED);
            } else if (donationItem.confirmed == 0) {
                mConfirmationText.setText("Awaiting Response");
                mConfirmationText.setBackgroundColor(Color.GRAY);
            } else {
                mConfirmationText.setText("Confirmed");
                mConfirmationText.setBackgroundColor(Color.GREEN);
            }

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goToSecondActivityIntent = new Intent(mContext, PantryViewDonationResponse.class);
                    goToSecondActivityIntent.putExtra("donationUUID", donationItem.UUID);
                    mContext.startActivity(goToSecondActivityIntent);
                }
            });
        }
    }
}

