package com.example.pantreasy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorResponseItemAdapter extends RecyclerView.Adapter {
    public DonorResponseItem mSelectedItem;
    private RadioButton mSelectedButton;
    private Context mContext;
    private ArrayList<DonorResponseItem> mResponseItems;

    public DonorResponseItemAdapter(Context context, ArrayList<DonorResponseItem> responseItems) {
        mContext = context;
        mResponseItems = responseItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // here, we specify what kind of view each cell should have. In our case, all of them will have a view
        View view = LayoutInflater.from(mContext).inflate(R.layout.donor_response_item, parent, false);
        return new ResponseItemViewHolder(view);
    }


    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DonorResponseItem responseItem = mResponseItems.get(position);
        ((ResponseItemViewHolder) holder).bind(responseItem);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mResponseItems.size();
    }

    class ResponseItemViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ConstraintLayout mResponseItemLayout;
        public TextView mPantryName;
        public ImageView mPantryImage;
        public TextView mPhoneNumberText;
        public TextView mAddressText;
        public TextView mCommentText;
        public FirebaseManager mFirebaseManager;
        public OnSuccessListener<byte[]> mImageSuccessListener;
        public ValueEventListener mProfileListener;
        public Profile mProfile;
        public View.OnClickListener mClickListener;
        public RadioButton mRadioButton;
        public DonorResponseItem mResponseItem;

        public ResponseItemViewHolder(View itemView) {
            super(itemView);
            mFirebaseManager = new FirebaseManager((DonorsPantryResponsesActivities)mContext);
            mResponseItemLayout = itemView.findViewById(R.id.response_item);
            mPantryName = mResponseItemLayout.findViewById(R.id.pantry_name);
            mPantryImage = mResponseItemLayout.findViewById(R.id.pantry_image);
            mPhoneNumberText = mResponseItemLayout.findViewById(R.id.phone_number_text);
            mAddressText = mResponseItemLayout.findViewById(R.id.address_text);
            mCommentText = mResponseItemLayout.findViewById(R.id.time_text);
            mRadioButton = mResponseItemLayout.findViewById(R.id.donors_pantry_response_item_radio_button);
            mImageSuccessListener = new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    mPantryImage.setImageBitmap(mFirebaseManager.bitmapFromBytes(bytes));
                }
            };

            mProfileListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mProfile = mFirebaseManager.getProfileFromDataSnapshot(dataSnapshot);
                    mFirebaseManager.imageFromStorage(mProfile.imageName, mImageSuccessListener);
                    mPhoneNumberText.setText(mProfile.phoneNumber);
                    mAddressText.setText(mProfile.address);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
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
            mResponseItem = responseItem;
            mPantryName.setText(responseItem.pantryProfileName);
            mFirebaseManager.getProfile(responseItem.pantryProfileName, mProfileListener);
            mCommentText.setText(responseItem.comment);
            mResponseItemLayout.setOnClickListener(mClickListener);
        }
    }
}

