package com.example.cookingapp.ui.activity.foodDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.data.model.RateModel;
import com.example.cookingapp.data.model.StepModel;
import com.example.cookingapp.data.model.UserModel;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.adapter.StepAdapter;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.helper.ObjectHelper;
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
    private Context context;

    private ImageView imgDetailsFood;
    private TextView txtDetailsFoodName;
    private TextView txtUploadDate;
    private TextView txtDetailsTime;
    private TextView txtDetailsCountry;
    private RatingBar rtbDetailsDifficultLevel;
    private RatingBar rtbDetailsRating;
    private TextView txtDetailsVote;
    private TextView txtDetailsRating;
    private TextView txtDetailsIsVegetarian;
    private TextView txtDetailsIngredient;
    private TextView txtDetailsRateFood;
    private RatingBar rtbDetailsRateFood;
    private Button btnDetailsRateFood;
    private ConstraintLayout layoutLoading;

    private FoodModel food;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        context = getApplicationContext();

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
        txtDetailsRating = findViewById(R.id.txtDetailsRating);
        txtDetailsIsVegetarian = findViewById(R.id.txtDetailsIsVegetarian);
        txtDetailsIngredient = findViewById(R.id.txtDetailsIngredient);
        txtDetailsRateFood = findViewById(R.id.txtDetailsRateFood);
        rtbDetailsRateFood = findViewById(R.id.rtbDetailsRateFood);
        btnDetailsRateFood = findViewById(R.id.btnDetailsRateFood);
        layoutLoading = findViewById(R.id.layoutLoading);
        final RecyclerView rvSteps = findViewById(R.id.layout_step_list);

        // Attach adapter to recycle view
        rvSteps.setAdapter(stepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void getFoodInfo() {
        final Intent intent = getIntent();
        final int foodId = intent.getIntExtra(BundleConstant.FOOD, 0);
        user = ObjectHelper.fromJson(intent.getStringExtra(BundleConstant.ACCOUNT), UserModel.class);

        Callback<FoodModel> callback = new Callback<FoodModel>() {
            @Override
            public void onResponse(@NonNull Call<FoodModel> call,
                                   Response<FoodModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                final FoodModel data = response.body();
                if (data != null) {
                    UiHelper.fade(layoutLoading, 600);
                    food = data;
                    bindData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodModel> call, @NonNull Throwable t) { }
        };
        new HttpService<>(this).instance(FoodService.class)
            .getFoodById(foodId)
            .enqueue(callback);
    }

    private void bindData() {
        UiHelper.setImageBitmapAsync(imgDetailsFood, food.image.name, this);

        final int difficultLevel = food.difficultLevel <= 1
                                   ? 1
                                   : food.difficultLevel >= 3 ? 3 : 2;

        txtDetailsFoodName.setText(food.name);
        txtUploadDate.setText(new SimpleDateFormat("dd/MM/yy", Locale.US).format(food.timePost));
        txtDetailsTime.setText(context.getString(R.string.txt_details_time_to_cook, food.timeToCook));
        txtDetailsCountry.setText(context.getString(R.string.txt_details_country, food.country.name));
        rtbDetailsRating.setRating(food.voteAvg);
        txtDetailsRating.setText(String.format(Locale.US, "%.1f", food.voteAvg));
        txtDetailsVote.setText(context.getString(R.string.txt_details_votes, food.voteCount));
        rtbDetailsDifficultLevel.setRating(difficultLevel);
        txtDetailsIngredient.setText(context.getString(R.string.txt_details_ingredients, food.ingredient));
        stepAdapter.addSteps(food.steps);

        if (!food.isVegetarian) {
            txtDetailsIsVegetarian.setVisibility(View.GONE);
        }

        final RateModel myRate = food.rates.stream()
            .filter(rate -> rate.idOwner == user.id)
            .findFirst()
            .orElse(null);
        if (myRate != null) {
            txtDetailsRateFood.setText(context.getString(R.string.txt_details_my_rate));
            rtbDetailsRateFood.setRating(myRate.rate);
            rtbDetailsRateFood.setIsIndicator(true);
            btnDetailsRateFood.setVisibility(View.GONE);
        }
        else {
            btnDetailsRateFood.setOnClickListener(view -> {

            });
        }
    }
}
