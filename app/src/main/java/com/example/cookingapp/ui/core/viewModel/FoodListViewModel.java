package com.example.cookingapp.ui.core.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookingapp.data.model.FoodModel;

import java.util.List;

public class FoodListViewModel extends ViewModel {
    private final MutableLiveData<List<FoodModel>> foods = new MutableLiveData<>();

    public LiveData<List<FoodModel>> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodModel> foodsList) {
        foods.setValue(foodsList);
    }
}
