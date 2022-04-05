package com.example.cookingapp.ui.activity.foodDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.data.model.StepModel;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.adapter.StepAdapter;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.helper.UiHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity {
    private final ArrayList<StepModel> steps = new ArrayList<>();
    private final StepAdapter<FoodDetailsActivity> stepAdapter = new StepAdapter<>(steps, this);

    private ImageView imgDetailsFood;
    private TextView txtDetailsFoodName;
    private TextView txtUploadDate;
    private TextView txtDetailsTime;
    private TextView txtDetailsCountry;
    private RatingBar rtbDetailsDifficultLevel;
    private RatingBar rtbDetailsRating;
    private TextView txtDetailsVote;
    private TextView txtDetailsIsVegetarian;
    private TextView txtDetailsIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        bindComponents();
        getFoodInfo();
    }

    private void bindComponents() {
        imgDetailsFood = findViewById(R.id.imgDetailsFood);
        txtDetailsFoodName = findViewById(R.id.txtDetailsFoodName);
        txtUploadDate = findViewById(R.id.txtUploadDate);
        txtDetailsTime = findViewById(R.id.txtDetailsTime);
        txtDetailsCountry = findViewById(R.id.txtDetailsCountry);
        rtbDetailsRating = findViewById(R.id.rtbDetailsRating);
        rtbDetailsDifficultLevel = findViewById(R.id.rtbDetailsDifficultLevel);
        txtDetailsVote = findViewById(R.id.txtDetailsVote);
        txtDetailsIsVegetarian = findViewById(R.id.txtDetailsIsVegetarian);
        txtDetailsIngredient = findViewById(R.id.txtDetailsIngredient);
        final RecyclerView rvSteps = findViewById(R.id.layout_step_list);

        // Attach adapter to recycle view
        rvSteps.setAdapter(stepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void getFoodInfo() {
        final Intent intent = getIntent();
        final int foodId = intent.getIntExtra(BundleConstant.FOOD, 0);

        Callback<FoodModel> callback = new Callback<FoodModel>() {
            @Override
            public void onResponse(@NonNull Call<FoodModel> call,
                                   Response<FoodModel> response) {
                final FoodModel data = response.body();
                if (data != null) {
                    bindData(data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodModel> call, @NonNull Throwable t) { }
        };
        new HttpService<>(this).instance(FoodService.class)
            .getFoodById(foodId)
            .enqueue(callback);
    }

    private void bindData(FoodModel foodModel) {
        UiHelper.setImageBitmapAsync(imgDetailsFood, foodModel.image.name, this);

        final int difficultLevel = foodModel.difficultLevel <= 1
                             ? 1
                             : foodModel.difficultLevel >= 3 ? 3 : 2;

        txtDetailsFoodName.setText(foodModel.name);
        txtUploadDate.setText(new SimpleDateFormat("dd/MM/yy", Locale.US).format(foodModel.timePost));
        txtDetailsTime.setText(getApplicationContext().getString(R.string.txt_details_time_to_cook, foodModel.timeToCook));
        txtDetailsCountry.setText(getApplicationContext().getString(R.string.txt_details_country, foodModel.country.name));
        rtbDetailsRating.setRating(foodModel.voteAvg);
        txtDetailsVote.setText(getApplicationContext().getString(R.string.txt_details_votes, foodModel.voteCount));
        rtbDetailsDifficultLevel.setRating(difficultLevel);
        txtDetailsIngredient.setText(getApplicationContext().getString(R.string.txt_details_ingredients, foodModel.ingredient));
        stepAdapter.addSteps(foodModel.steps);

        if (!foodModel.isVegetarian) {
            txtDetailsIsVegetarian.setVisibility(View.GONE);
        }
    }
}
