package com.example.cookingapp.ui.activity.addFood;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.AddNewFoodDto;
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.data.ui.StepUiModel;
import com.example.cookingapp.databinding.ActivityAddFoodBinding;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.adapter.AddStepAdapter;
import com.example.cookingapp.ui.adapter.SpinnerAdapter;
import com.example.cookingapp.ui.core.view.AppImageView;
import com.example.cookingapp.util.constant.AppConstant;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.constant.HttpConstant;
import com.example.cookingapp.util.helper.ObjectHelper;
import com.example.cookingapp.util.helper.UiHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

import kotlin.Unit;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodActivity extends AppCompatActivity {
    private final ArrayList<StepUiModel> steps = new ArrayList<>();
    private final AddStepAdapter addStepAdapter = new AddStepAdapter(this, steps);

    private EditText txtFoodName;
    private Spinner spnCountry;
    private CheckBox cbIsVegetarian;
    private RatingBar ratingBar;
    private EditText txtTimeToCook;
    private EditText txtDescription;
    private EditText txtIngredient;
    private EditText txtTips;
    private Button btnAddStep;
    private Button btnAddFinish;
    private Button btnUploadImage;
    private AppImageView imgFood;
    private RecyclerView rvSteps;

    private Button imageButtonHolder;
    private AppImageView imageHolder;

    ActivityResultLauncher<Intent> getImage =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            final int resultCode = result.getResultCode();
            if (resultCode == Activity.RESULT_OK) {
                final Intent data = result.getData();
                if (data != null) {
                    imageButtonHolder.setVisibility(View.GONE);
                    imageHolder.setVisibility(View.VISIBLE);
                    imageHolder.setImageURI(data.getData());
                }
            }
        });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view
        final ActivityAddFoodBinding binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Config action bar
        UiHelper.displayActionBarNavigateBackButton(this);

        // Binding components
        bindComponents();

        // Add events
        addEventToAddFinishButton();
        addEventToAddStepButton();
        addEventToUploadImageComponents();

        // Attach adapter to recycle view
        rvSteps.setAdapter(addStepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Load data
        loadCountriesData();
    }

    public void changeImage(Button imageButtonHolder, AppImageView imageHolder) {
        this.imageButtonHolder = imageButtonHolder;
        this.imageHolder = imageHolder;

        ImagePicker.with(this)
            .cropSquare()
            .compress(AppConstant.MAX_IMAGE_FILE_SIZE)
            .createIntent(intent -> {
                getImage.launch(intent);
                return Unit.INSTANCE;
            });
    }

    private void bindComponents() {
        txtFoodName = findViewById(R.id.txtFoodName);
        spnCountry = findViewById(R.id.spnCountry);
        cbIsVegetarian = findViewById(R.id.cbIsVegetarian);
        ratingBar = findViewById(R.id.rtbDetailsRating);
        txtTimeToCook = findViewById(R.id.txtTimeToCook);
        txtDescription = findViewById(R.id.txtDescription);
        txtIngredient = findViewById(R.id.txtIngredient);
        txtTips = findViewById(R.id.txtTips);
        btnAddStep = findViewById(R.id.btnAddStep);
        btnAddFinish = findViewById(R.id.btnAddFinish);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        imgFood = findViewById(R.id.imgUploadFood);
        rvSteps = findViewById(R.id.layout_step_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ratingBar.setMin(1);
        }
    }

    private void addEventToAddFinishButton() {
        btnAddFinish.setOnClickListener(view -> {
            final AddNewFoodDto addNewFoodDto;
            try {
                addNewFoodDto = generateDto();
            }
            catch (Exception e) {
                return;
            }

            final int stepsCount = addStepAdapter.getItemCount();
            if (stepsCount == 0) {
                showToast("Vui lòng nhập các bước thực hiện");
                return;
            }

            // Main image
            final Uri mainImageFileUri = UiHelper.getUri(getApplicationContext(), imgFood.getId());
            final File mainImageFile = new File(mainImageFileUri.getPath());
            final RequestBody mainImage = RequestBody.create(HttpConstant.IMAGE, mainImageFile);
            final MultipartBody.Part mainImagePart =
                MultipartBody.Part.createFormData("mainImage", mainImageFile.getName(), mainImage);

            final MultipartBody.Part[] stepImageParts = new MultipartBody.Part[stepsCount];

            for (int i = 0; i < stepsCount; i++) {
                final AddStepAdapter.ViewHolder stepView =
                    (AddStepAdapter.ViewHolder) rvSteps.findViewHolderForAdapterPosition(i);
                if (stepView == null) {
                    continue;
                }

                final Uri fileUri = UiHelper.getUri(getApplicationContext(), stepView.imgStep.getId());
                final File file = new File(fileUri.getPath());
                final RequestBody stepBody = RequestBody.create(HttpConstant.IMAGE, file.getName());
                stepImageParts[i] = MultipartBody.Part.createFormData("stepImage", file.getName(), stepBody);

                final StepUiModel stepData = addStepAdapter.getSteps().get(i);
                addNewFoodDto.steps.add(
                    new AddNewFoodDto.AddNewFoodStepDto(stepData.getName(), stepData.getDescription()));
            }

            final RequestBody requestBody = RequestBody.create(HttpConstant.JSON, ObjectHelper.toJson(addNewFoodDto));

            new HttpService<>(this).instance(FoodService.class)
                .addNewFood(mainImagePart, stepImageParts, requestBody)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {}

                    @Override
                    public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) { }
                });
        });
    }

    private void addEventToAddStepButton() {
        btnAddStep.setOnClickListener(view -> {
            addStepAdapter.addNewStep();
            rvSteps.smoothScrollToPosition(addStepAdapter.getItemCount() - 1);
        });
    }

    private void addEventToUploadImageComponents() {
        btnUploadImage.setOnClickListener(view -> changeImage());
        imgFood.setOnClickListener(view -> changeImage());
    }

    private void loadCountriesData() {
        final ArrayList<CountryModel> countriesList =
            new ArrayList<>(getIntent().getParcelableArrayListExtra(BundleConstant.COUNTRY));

        ArrayAdapter<CountryModel> adapter = new SpinnerAdapter<>(this, countriesList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnCountry.setAdapter(adapter);
    }

    private AddNewFoodDto generateDto() throws Exception {
        final String name = txtFoodName.getText().toString();
        final String country = ((CountryModel) spnCountry.getSelectedItem()).code;
        final boolean isVegetarian = cbIsVegetarian.isChecked();
        final int difficultLevel = (int) ratingBar.getRating();
        final String timeToCookStr = txtTimeToCook.getText().toString();
        final String description = txtDescription.getText().toString();
        final String ingredient = txtIngredient.getText().toString();
        final String tips = txtTips.getText().toString();

        if (name.isEmpty()) {
            showToast("Vui lòng nhập tên món ăn");
            throw new Exception();
        }
        if (country.isEmpty()) {
            showToast("Vui lòng chọn quốc gia");
            throw new Exception();
        }
        if (timeToCookStr.isEmpty()) {
            showToast("Vui lòng nhập thời gian nấu");
            throw new Exception();
        }
        if (description.isEmpty()) {
            showToast("Vui lòng nhập mô tả");
            throw new Exception();
        }
        if (ingredient.isEmpty()) {
            showToast("Vui lòng nhập các thành phần");
            throw new Exception();
        }

        final int timeToCook = Integer.parseInt(timeToCookStr);

        return new AddNewFoodDto(name, country, difficultLevel, isVegetarian, description, timeToCook, ingredient, tips);
    }

    private void changeImage() {
        changeImage(this.btnUploadImage, this.imgFood);
    }

    private void showToast(String text) {
        UiHelper.showToast(this, text);
    }
}
