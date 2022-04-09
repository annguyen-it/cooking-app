package com.example.cookingapp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.ui.activity.foodDetails.FoodDetailsActivity;
import com.example.cookingapp.ui.core.event.OnItemClickListener;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.helper.UiHelper;

import java.util.ArrayList;
import java.util.List;

public class GridFoodAdapter<A extends FragmentActivity>
    extends RecyclerView.Adapter<GridFoodAdapter<A>.ViewHolder>
    implements OnItemClickListener<FoodModel> {

    private List<FoodModel> listFood = new ArrayList<>();
    private final A hostActivity;

    public GridFoodAdapter(A hostActivity) {
        this.hostActivity = hostActivity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListFood(List<FoodModel> listFood) {
        this.listFood = listFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GridFoodAdapter<A>.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_search_food, parent, false);

        // Return a new holder instance
        return new GridFoodAdapter<A>.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listFood.get(position), this);
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    @Override
    public void onItemClick(FoodModel item) {
        final Intent intent = new Intent(hostActivity, FoodDetailsActivity.class);
        final Intent activityIntent = hostActivity.getIntent();
        final String accountExtra = activityIntent.getStringExtra(BundleConstant.ACCOUNT);

        intent.putExtra(BundleConstant.FOOD_ID, item.id);
        intent.putExtra(BundleConstant.ACCOUNT, accountExtra);

        hostActivity.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgFood;
        public final TextView txtNameFood;
        public final RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            txtNameFood = itemView.findViewById(R.id.txtNameFood);
            ratingBar = itemView.findViewById(R.id.rtbDetailsRating);
        }

        public void bind(@NonNull FoodModel food, OnItemClickListener<FoodModel> listener) {
            txtNameFood.setText(food.name);
            ratingBar.setRating(food.voteAvg);
            UiHelper.setImageBitmapAsync(imgFood, food.image.name, hostActivity);

            itemView.setOnClickListener(view -> listener.onItemClick(food));
        }
    }
}
