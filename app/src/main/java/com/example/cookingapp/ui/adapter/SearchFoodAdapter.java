package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;

import java.util.List;

public class SearchFoodAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private List<FoodModel> lstFood;

    public SearchFoodAdapter(Context context, int layout, List<FoodModel> lstFood) {
        this.context = context;
        this.layout = layout;
        this.lstFood = lstFood;
    }

    public List<FoodModel> getLstFood() {
        return lstFood;
    }

    public void setLstFood(List<FoodModel> lstFood) {
        this.lstFood = lstFood;
    }

    @Override
    public int getCount() {
        return lstFood.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgFood);
        TextView txtNameFood = view.findViewById(R.id.txtNameFood);
        TextView txtNameOwner = view.findViewById(R.id.txtNameOwner);
        RatingBar ratingBar = view.findViewById(R.id.rtbDetailsRating);

        FoodModel food = lstFood.get(i);
        txtNameFood.setText(food.name);
        txtNameOwner.setText(food.owner.fullName);
        ratingBar.setRating(food.voteAvg);

        return view;
    }
}
