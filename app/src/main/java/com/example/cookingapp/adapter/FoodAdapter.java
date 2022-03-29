package com.example.cookingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.model.Food;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Food> lstFood;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Food> getLstFood() {
        return lstFood;
    }

    public void setLstFood(List<Food> lstFood) {
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

        view = inflater.inflate(layout,null);

        ImageView imgFood = (ImageView) view.findViewById(R.id.imgFood);
        TextView txtNameFood = view.findViewById(R.id.txtNameFood);
        TextView txtNameOwner = view.findViewById(R.id.txtNameOwner);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        Food food = lstFood.get(i);
        imgFood.setImageResource(food.getIdImage());
        txtNameFood.setText(food.getName());
        txtNameOwner.setText(food.getUserName());
        ratingBar.setRating(food.getRate());

        return view;
    }

}
