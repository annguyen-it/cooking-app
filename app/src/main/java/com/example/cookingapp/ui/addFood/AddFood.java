package com.example.cookingapp.ui.addFood;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.Step;
import com.example.cookingapp.databinding.ActivityAddFoodBinding;
import com.example.cookingapp.ui.adapter.AddStepAdapter;

import java.util.ArrayList;

public class AddFood extends AppCompatActivity {
    private final ArrayList<Step> steps = new ArrayList<>();
    private final AddStepAdapter addStepAdapter = new AddStepAdapter(steps);
    private Button btnAddFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view
        final ActivityAddFoodBinding binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Binding components
        btnAddFood = findViewById(R.id.btnAddStep);
        RecyclerView rvSteps = findViewById(R.id.layout_step_list);

        // Add events
        addEventToAddStepButton();

        // Attach adapter to recycle view
        rvSteps.setAdapter(addStepAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void addEventToAddStepButton() {
        btnAddFood.setOnClickListener(view -> {
            steps.add(new Step());
            addStepAdapter.notifyItemInserted(addStepAdapter.getItemCount() - 1);
        });
    }
}
