package com.example.pantreasy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PantryFoodItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<FoodItem> mFoodItems;
    private boolean mShowCheckBox;

    public PantryFoodItemAdapter(Context context, List<FoodItem> foodItems, boolean showCheckBox, boolean PantryViewDonationResponse) {
        mContext = context;
        mFoodItems = foodItems;
        mShowCheckBox = showCheckBox;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pantry_food_item, parent, false);
        return new PantryFoodItemViewHolder(view);
    }

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

    class PantryFoodItemViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ConstraintLayout mFoodItemLayout;
        public TextView mFoodNameView;
        public TextView mExpDateView;
        public TextView mQuantityView;
        public TextView mPerishableView;
        public ImageView mFoodImage;
        public CheckBox mCheckBox;

        public PantryFoodItemViewHolder(View itemView) {
            super(itemView);
            mFoodItemLayout = itemView.findViewById(R.id.food_item);
            mFoodNameView = mFoodItemLayout.findViewById(R.id.food_name);
            mExpDateView = mFoodItemLayout.findViewById(R.id.expiration_text);
            mQuantityView = mFoodItemLayout.findViewById(R.id.quantity_text);
            mPerishableView = mFoodItemLayout.findViewById(R.id.perishable_text);
            mFoodImage = mFoodItemLayout.findViewById(R.id.food_image);
            mCheckBox = mFoodItemLayout.findViewById(R.id.checkBox);
            mCheckBox.setChecked(true);
        }

        void bind(FoodItem foodItem) {
            mFoodImage.setImageBitmap(((Pantreasy) mContext.getApplicationContext()).mAllPictures.get(foodItem.imageName));
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

