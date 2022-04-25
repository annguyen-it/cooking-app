package com.example.cookingapp.ui.activity.foodList;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.core.viewModel.FoodListViewModel;
import com.example.cookingapp.util.helper.UiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFoodListActivity extends AppCompatActivity {
    private FoodListViewModel foodListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food);

        // Config action bar
        UiHelper.displayActionBarNavigateBackButton(this);

        // Binding components
        foodListViewModel = new ViewModelProvider(this).get(FoodListViewModel.class);

        // Load data
        loadMyFood();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    private void loadMyFood() {
        final Callback<List<FoodModel>> callback = new Callback<List<FoodModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<FoodModel>> call, @NonNull Response<List<FoodModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<FoodModel> foods = response.body();
                if (foods != null) {
                    foodListViewModel.setFoods(foods);
                    foodListViewModel.setLoading(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FoodModel>> call, @NonNull Throwable t) { }
        };

        new HttpService<>(this).instance(FoodService.class)
            .getMyFood(1, 0)
            .enqueue(callback);
    }
}
