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

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.util.helper.UiHelper;

import java.util.List;

public class GridFoodAdapter<A extends AppCompatActivity> extends BaseAdapter {
    private final Context context;
    private final int layout;
    private List<FoodModel> listFood;
    private final A activity;

    public GridFoodAdapter(Context context, int layout, List<FoodModel> listFood, A activity) {
        this.context = context;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        ImageView imageView = view.findViewById(R.id.imgFood);
        TextView txtNameFood = view.findViewById(R.id.txtNameFood);
        RatingBar ratingBar = view.findViewById(R.id.rtbDetailsRating);

        FoodModel food = listFood.get(i);

        txtNameFood.setText(food.name);
        ratingBar.setRating(food.voteAvg);
        UiHelper.setImageBitmapAsync(imageView, food.image.name, activity);

        return view;
    }
}
