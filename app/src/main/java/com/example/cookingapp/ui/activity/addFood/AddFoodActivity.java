package com.example.cookingapp.ui.activity.addFood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.ui.StepUiModel;
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.databinding.ActivityAddFoodBinding;
import com.example.cookingapp.service.http.CountryService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.adapter.AddStepAdapter;
import com.example.cookingapp.ui.adapter.SpinnerAdapter;
import com.example.cookingapp.util.constant.AppConstant;
import com.example.cookingapp.util.constant.HttpConstant;
import com.example.cookingapp.util.helper.UiHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodActivity extends AppCompatActivity {
    private final ArrayList<StepUiModel> steps = new ArrayList<>();
    private final AddStepAdapter addStepAdapter = new AddStepAdapter(this, steps);

    private Button btnAddStep;
    private Button btnAddFinish;
    private Button btnUploadImage;
    private Spinner spnCountry;
    private ImageView imgFood;
    private RecyclerView rvSteps;

    private Button imageButtonHolder;
    private ImageView imageHolder;

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
        btnAddStep = findViewById(R.id.btnAddStep);
        btnAddFinish = findViewById(R.id.btnAddFinish);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        spnCountry = findViewById(R.id.spnCountry);
        imgFood = findViewById(R.id.imgUploadFood);
        rvSteps = findViewById(R.id.layout_step_list);

        // Add events
        addEventToAddFinishButton();
        addEventToAddStepButton();
        addEventToUploadImageComponents();

        // Attach adapter to recycle view
        rvSteps.setAdapter(addStepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Load data
        getCountriesData();
    }

    public void uploadImage(Button imageButtonHolder, ImageView imageHolder) {
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

    private void addEventToAddFinishButton() {
        btnAddFinish.setOnClickListener(view -> {
            final int stepsCount = addStepAdapter.getItemCount();

            // Main image
            final File mainImageFile = new File(UiHelper.getUri(imgFood.getId()).getPath());
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

                final File file = new File(UiHelper.getUri(stepView.imgStep.getId()).getPath());
                final RequestBody stepBody = RequestBody.create(HttpConstant.IMAGE, file.getName());
                stepImageParts[i] = MultipartBody.Part.createFormData("stepImage", file.getName(), stepBody);
            }

            System.out.println(addStepAdapter.getSteps());
            //            new HttpService<>(this).instance(FoodService.class)
//                .addNewFood(mainImagePart, stepImageParts, null)
//                .enqueue(new Callback<LoginModel>() {
//                    @Override
//                    public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {}
//
//                    @Override
//                    public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) { }
//                });
        });
    }

    private void addEventToAddStepButton() {
        btnAddStep.setOnClickListener(view -> {
            addStepAdapter.addNewStep();
            rvSteps.smoothScrollToPosition(addStepAdapter.getItemCount() - 1);
        });
    }

    private void addEventToUploadImageComponents() {
        btnUploadImage.setOnClickListener(view -> uploadImage());
        imgFood.setOnClickListener(view -> uploadImage());
    }

    private void getCountriesData() {
        final AddFoodActivity thisActivity = this;
        new HttpService<>(this).instance(CountryService.class)
            .getCountries()
            .enqueue(new Callback<List<CountryModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CountryModel>> call, @NonNull Response<List<CountryModel>> response) {
                    final List<CountryModel> countriesList = response.body();
                    if (countriesList == null) {
                        return;
                    }

                    countriesList.add(0, new CountryModel().defaultValue());
                    ArrayAdapter<CountryModel> adapter = new SpinnerAdapter<>(thisActivity, countriesList);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spnCountry.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable t) { }
            });
    }

    private void uploadImage() {
        uploadImage(this.btnUploadImage, this.imgFood);
    }
}
