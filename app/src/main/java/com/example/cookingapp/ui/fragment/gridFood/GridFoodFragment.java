package com.example.cookingapp.ui.fragment.gridFood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.ui.adapter.GridFoodAdapter;
import com.example.cookingapp.ui.core.view.AppLoadingView;
import com.example.cookingapp.ui.core.viewModel.FoodListViewModel;

public class GridFoodFragment extends Fragment {
    private FragmentActivity hostActivity;
    private GridFoodAdapter<? extends FragmentActivity> searchFoodAdapter;
    private AppLoadingView loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grid_food, container, false);
        hostActivity = requireActivity();
        searchFoodAdapter = new GridFoodAdapter<>(hostActivity);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindComponents(view);
        bindViewModels();
    }

    private void bindComponents(@NonNull View view) {
        loading = view.findViewById(R.id.loading);
        RecyclerView rvFood = view.findViewById(R.id.grvFood);
        rvFood.setAdapter(searchFoodAdapter);

        final GridLayoutManager layoutManager = new GridLayoutManager(hostActivity, 2);
        rvFood.setLayoutManager(layoutManager);
    }

    private void bindViewModels() {
        final FoodListViewModel foodListViewModel = new ViewModelProvider(hostActivity).get(FoodListViewModel.class);
        final LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        foodListViewModel.getFoods()
            .observe(viewLifecycleOwner, foodModels -> searchFoodAdapter.setListFood(foodModels));
        foodListViewModel.getLoading()
            .observe(viewLifecycleOwner, isLoading -> {
                if (isLoading) {
                    loading.fadeIn();
                }
                else {
                    loading.fadeOut();
                }
            });
    }
}
