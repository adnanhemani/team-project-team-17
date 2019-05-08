package com.example.pantreasy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorResponseItemAdapter extends RecyclerView.Adapter {
    public DonorResponseItem mSelectedItem;
    private RadioButton mSelectedButton;
    private Context mContext;
    private ArrayList<DonorResponseItem> mResponseItems;

    public DonorResponseItemAdapter(Context context, ArrayList<DonorResponseItem> responseItems) {
        mContext = context;
        ArrayList<DonorResponseItem> l = new ArrayList<>();
        for (int i = 0; i < responseItems.size(); i++) {
            DonorResponseItem item = responseItems.get(i);
            if (item.confirmed == 0)
                l.add(item);
        }
        mResponseItems = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.donor_response_item, parent, false);
        return new ResponseItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DonorResponseItem responseItem = mResponseItems.get(position);
        ((ResponseItemViewHolder) holder).bind(responseItem);
    }

    @Override
    public int getItemCount() {
        return mResponseItems.size();
    }

    class ResponseItemViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout mResponseItemLayout;
        public TextView mPantryName;
        public ImageView mPantryImage;
        public TextView mPhoneNumberText;
        public TextView mAddressText;
        public TextView mCommentText;
        public View.OnClickListener mClickListener;
        public RadioButton mRadioButton;
        public DonorResponseItem mResponseItem;

        public ResponseItemViewHolder(View itemView) {
            super(itemView);
            mResponseItemLayout = itemView.findViewById(R.id.response_item);
            mPantryName = mResponseItemLayout.findViewById(R.id.pantry_name);
            mPantryImage = mResponseItemLayout.findViewById(R.id.pantry_image);
            mPhoneNumberText = mResponseItemLayout.findViewById(R.id.phone_number_text);
            mAddressText = mResponseItemLayout.findViewById(R.id.address_text);
            mCommentText = mResponseItemLayout.findViewById(R.id.time_text);
            mRadioButton = mResponseItemLayout.findViewById(R.id.donors_pantry_response_item_radio_button);

            mClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectedButton != null)
                        mSelectedButton.setChecked(false);
                    mSelectedButton = mRadioButton;
                    mSelectedButton.setChecked(true);
                    mSelectedItem = mResponseItem;
                }
            };

        }

        void bind(DonorResponseItem responseItem) {
            Pantreasy pantreasy = (Pantreasy)mContext.getApplicationContext();
            Profile pantryProfile = pantreasy.pantryProfiles.get(responseItem.pantryProfileName);
            mPantryImage.setImageBitmap(pantreasy.mPictures.get(pantryProfile.imageName));
            mResponseItem = responseItem;
            mPantryName.setText(responseItem.pantryProfileName);
            mCommentText.setText(responseItem.comment);
            mResponseItemLayout.setOnClickListener(mClickListener);
            mRadioButton.setOnClickListener(mClickListener);
        }
    }
}

