package com.example.cookingapp.ui.activity.foodDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.data.model.RateModel;
import com.example.cookingapp.data.model.StepModel;
import com.example.cookingapp.data.model.UserModel;
import com.example.cookingapp.data.ui.FoodUiModel;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.adapter.RatedAdapter;
import com.example.cookingapp.ui.adapter.StepAdapter;
import com.example.cookingapp.ui.core.view.AppLoadingView;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.helper.ObjectHelper;
import com.example.cookingapp.util.helper.UiHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity {
    private final ArrayList<StepModel> steps = new ArrayList<>();
    private final StepAdapter<FoodDetailsActivity> stepAdapter = new StepAdapter<>(steps, this);
    private final RatedAdapter ratedAdapter = new RatedAdapter(this,R.layout.item_rated,new ArrayList<>());
    private Context context;

    private AppLoadingView loading;
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
    private Button btnDetailsRateFood;
    private ListView lstViewRate;

    private FoodModel food;
    private UserModel user;

    private int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        context = getApplicationContext();

        bindComponents();
        getFoodInfo();
    }

    private void bindComponents() {
        loading = findViewById(R.id.loading);
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
        btnDetailsRateFood = findViewById(R.id.btnDetailsRateFood);
        lstViewRate = findViewById(R.id.lstViewRate);
        final RecyclerView rvSteps = findViewById(R.id.layout_step_list);

        // Attach adapter to recycle view
        rvSteps.setAdapter(stepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        lstViewRate.setAdapter(ratedAdapter);
    }

    private void getFoodInfo() {
        final Intent intent = getIntent();
        final int foodId = intent.getIntExtra(BundleConstant.FOOD_ID, 0);
        user = ObjectHelper.fromJson(intent.getStringExtra(BundleConstant.ACCOUNT), UserModel.class);

        Callback<FoodModel> callback = new Callback<FoodModel>() {
            @Override
            public void onResponse(@NonNull Call<FoodModel> call,
                                   @NonNull Response<FoodModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                final FoodModel data = response.body();
                if (data != null) {
                    loading.fadeOut();
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
            .filter(rate -> rate.owner.id == user.id)
            .findFirst()
            .orElse(null);
        if (myRate != null) {
            txtDetailsRateFood.setText(context.getString(R.string.txt_details_my_rate));
            btnDetailsRateFood.setVisibility(View.GONE);
        }
        else {
            btnDetailsRateFood.setOnClickListener(view -> rate());
        }
        //getRate();
    }

    private void rate() {
        final Intent intent = new Intent(this, RateActivity.class);
        final FoodUiModel foodUiModel = new FoodUiModel(food);
        intent.putExtra(BundleConstant.FOOD, ObjectHelper.toJson(foodUiModel));

        startActivity(intent);
    }

    private void getRate(){
        Callback<List<RateModel>> callback = new Callback<List<RateModel>>() {
            @Override
            public void onResponse(Call<List<RateModel>> call, Response<List<RateModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                final List<RateModel> data = response.body();
                if (data != null) {
                    UiHelper.fade(layoutLoading, 600);

                    ratedAdapter.setListRated(data);

                }
            }

            @Override
            public void onFailure(Call<List<RateModel>> call, Throwable t) {

            }
        };
        new HttpService<>(this).instance(FoodService.class)
                .getRate(foodId)
                .enqueue(callback);
    }
}
