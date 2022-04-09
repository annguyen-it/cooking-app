package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.util.helper.UiHelper;

import java.util.List;

public class GridFoodAdapter<A extends FragmentActivity> extends BaseAdapter {
    private final int layout;
    private List<FoodModel> listFood;
    private final A activity;

    public GridFoodAdapter(A activity, int layout, List<FoodModel> listFood) {
        this.layout = layout;
        this.listFood = listFood;
        this.activity = activity;
    }

    public void setListFood(List<FoodModel> listFood) {
        this.listFood = listFood;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public FoodModel getItem(int i) {
        return listFood.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(layout, null);
        }

        ImageView imageView = view.findViewById(R.id.imgFood);
        TextView txtNameFood = view.findViewById(R.id.txtNameFood);
        RatingBar ratingBar = view.findViewById(R.id.rtbDetailsRating);

        FoodModel food = listFood.get(i);

        txtNameFood.setText(food.name);
        ratingBar.setRating(food.voteAvg);
//        UiHelper.setImageBitmapAsync(imageView, food.image.name, activity);

        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtDetailsStepName;
        public final TextView txtStepName;
        public final ImageView imgStep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDetailsStepName = itemView.findViewById(R.id.txtStepName);
            txtStepName = itemView.findViewById(R.id.txtStepDescription);
            imgStep = itemView.findViewById(R.id.imgStep);
        }
    }
}
