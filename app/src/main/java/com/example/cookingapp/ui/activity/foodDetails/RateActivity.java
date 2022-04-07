package com.example.cookingapp.ui.activity.foodDetails;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.RateDto;
import com.example.cookingapp.data.model.RateModel;
import com.example.cookingapp.data.ui.FoodUiModel;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.helper.ObjectHelper;
import com.example.cookingapp.util.helper.UiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateActivity extends AppCompatActivity {
    private RatingBar rtbRate;
    private EditText txtRateComment;

    private FoodUiModel food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        food = ObjectHelper.fromJson(getIntent().getStringExtra(BundleConstant.FOOD), FoodUiModel.class);

        bindComponents();
    }

    private void bindComponents() {
        rtbRate = findViewById(R.id.rtbRate);
        txtRateComment = findViewById(R.id.txtRateComment);
        TextView txtRateName = findViewById(R.id.txtRateName);
        Button btnRateConfirm = findViewById(R.id.btnRateConfirm);

        txtRateName.setText(food.name);
        btnRateConfirm.setOnClickListener(view -> tryRate());
    }

    private void tryRate() {
        if (rtbRate.getRating() == 0) {
            showToast("Bạn cần chọn số sao");
            return;
        }
        rate();
    }

    private void rate() {
        final int rate = rtbRate.getNumStars();
        final String comment = txtRateComment.getText().toString();
        final RateDto rateDto = new RateDto(rate, comment);

        Callback<RateModel> callback = new Callback<RateModel>() {
            @Override
            public void onResponse(@NonNull Call<RateModel> call,
                                   @NonNull Response<RateModel> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                showToast("Gửi đánh giá thành công");
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<RateModel> call, @NonNull Throwable t) { }
        };
        new HttpService<>(this).instance(FoodService.class)
            .rateFood(food.id, rateDto)
            .enqueue(callback);
    }

    private void showToast(String text) {
        UiHelper.showToast(this, text);
    }
}
