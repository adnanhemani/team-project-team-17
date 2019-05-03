package com.example.pantreasy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorResponseItemAdapter extends RecyclerView.Adapter {

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

        public ResponseItemViewHolder(View itemView) {
            super(itemView);
            mResponseItemLayout = itemView.findViewById(R.id.response_item);
            mPantryName = mResponseItemLayout.findViewById(R.id.pantry_name);
            mPantryImage = mResponseItemLayout.findViewById(R.id.pantry_image);
            mPhoneNumberText = mResponseItemLayout.findViewById(R.id.phone_number_text);
            mAddressText = mResponseItemLayout.findViewById(R.id.address_text);
            mCommentText = mResponseItemLayout.findViewById(R.id.comment_text);
        }

        void bind(DonorResponseItem responseItem) {
            mPantryName.setText(responseItem.pantryProfile.name);
            mPantryImage.setImageBitmap(responseItem.pantryProfile.imageBitmap);
            mPhoneNumberText.setText(responseItem.pantryProfile.phoneNumber);
            mAddressText.setText(responseItem.pantryProfile.address);
            mCommentText.setText(responseItem.comment);
        }
    }
}

