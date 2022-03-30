package com.example.cookingapp.ui.addFood;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.Step;
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.databinding.ActivityAddFoodBinding;
import com.example.cookingapp.service.http.CountryService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.adapter.AddStepAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodActivity extends AppCompatActivity {
    private final ArrayList<Step> steps = new ArrayList<>();
    private final AddStepAdapter addStepAdapter = new AddStepAdapter(steps);
    private Button btnAddFood;
    private Spinner spnCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view
        final ActivityAddFoodBinding binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Binding components
        btnAddFood = findViewById(R.id.btnAddStep);
        spnCountry = findViewById(R.id.spnCountry);
        RecyclerView rvSteps = findViewById(R.id.layout_step_list);

        // Add events
        addEventToAddStepButton();

        // Attach adapter to recycle view
        rvSteps.setAdapter(addStepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Load data
        getCountriesData();
    }

    private void addEventToAddStepButton() {
        btnAddFood.setOnClickListener(view -> {
            steps.add(new Step());
            addStepAdapter.notifyItemInserted(addStepAdapter.getItemCount() - 1);
        });
    }

    private void getCountriesData() {
        final AddFoodActivity thisActivity = this;
        new HttpService<>(this).instance(CountryService.class)
            .getCountries()
            .enqueue(new Callback<List<CountryModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CountryModel>> call, @NonNull Response<List<CountryModel>> response) {
                    final List<CountryModel> countriesList = response.body();
                    ArrayAdapter<CountryModel> adapter =
                        new ArrayAdapter<CountryModel>(thisActivity, R.layout.support_simple_spinner_dropdown_item, countriesList) {
                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                ((TextView) view).setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                                return super.getDropDownView(position, convertView, parent);
                            }
                        };
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spnCountry.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable t) { }
            });
    }
}
