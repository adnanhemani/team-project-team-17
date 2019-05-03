package com.example.pantreasy;

import android.content.Context;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PantryFoodItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<FoodItem> mFoodItems;
    private boolean mShowCheckBox;
    private boolean mPantryViewDonationResponseContext;

    public PantryFoodItemAdapter(Context context, List<FoodItem> foodItems, boolean showCheckBox, boolean PantryViewDonationResponse) {
        mContext = context;
        mFoodItems = foodItems;
        mShowCheckBox = showCheckBox;
        mPantryViewDonationResponseContext = PantryViewDonationResponse;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // here, we specify what kind of view each cell should have. In our case, all of them will have a view
        View view = LayoutInflater.from(mContext).inflate(R.layout.pantry_food_item, parent, false);
        return new PantryFoodItemViewHolder(view);
    }


    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodItem foodItem = mFoodItems.get(position);
        ((PantryFoodItemViewHolder) holder).bind(foodItem);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public void removeAt(int position) {
        mFoodItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mFoodItems.size());
    }

    class PantryFoodItemViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ConstraintLayout mFoodItemLayout;
        public TextView mFoodNameView;
        public TextView mExpDateView;
        public TextView mQuantityView;
        public TextView mPerishableView;
        public ImageView mFoodImage;
        public CheckBox mCheckBox;
        public FirebaseManager mFirebaseManager;
        public OnSuccessListener<byte[]> mImageSuccessListener;

        public PantryFoodItemViewHolder(View itemView) {
            super(itemView);
            mFirebaseManager = (mPantryViewDonationResponseContext) ? new FirebaseManager((PantryViewDonationResponse)mContext) : new FirebaseManager((PantryViewDonation)mContext);
            mFoodItemLayout = itemView.findViewById(R.id.food_item);
            mFoodNameView = mFoodItemLayout.findViewById(R.id.food_name);
            mExpDateView = mFoodItemLayout.findViewById(R.id.expiration_text);
            mQuantityView = mFoodItemLayout.findViewById(R.id.quantity_text);
            mPerishableView = mFoodItemLayout.findViewById(R.id.perishable_text);
            mFoodImage = mFoodItemLayout.findViewById(R.id.food_image);
            mCheckBox = mFoodItemLayout.findViewById(R.id.checkBox);
            mCheckBox.setChecked(true);
            mImageSuccessListener = new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    mFoodImage.setImageBitmap(mFirebaseManager.bitmapFromBytes(bytes));
                }
            };
        }

        void bind(FoodItem foodItem) {
            mFirebaseManager.imageFromStorage(foodItem.imageName, mImageSuccessListener);
            mFoodImage.setClipToOutline(true);
            mFoodNameView.setText(foodItem.name);
            mExpDateView.setText("Expires on: " + foodItem.expirationDate);
            mQuantityView.setText("Quantity: " + foodItem.quantity);
            if (foodItem.perishable)
                mPerishableView.setText("Perishable: Yes");
            else
                mPerishableView.setText("Perishable: No");
            if (!mShowCheckBox)
                mCheckBox.setVisibility(View.GONE);
        }
    }
}

