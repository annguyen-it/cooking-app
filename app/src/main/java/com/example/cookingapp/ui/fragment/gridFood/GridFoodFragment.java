package com.example.cookingapp.ui.fragment.gridFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookingapp.R;
import com.example.cookingapp.ui.activity.foodDetails.FoodDetailsActivity;
import com.example.cookingapp.ui.adapter.GridFoodAdapter;
import com.example.cookingapp.ui.core.view.AppLoadingView;
import com.example.cookingapp.ui.core.viewModel.FoodListViewModel;
import com.example.cookingapp.util.constant.BundleConstant;

import java.util.ArrayList;

public class GridFoodFragment extends Fragment {
    private GridFoodAdapter searchFoodAdapter;
    private AppLoadingView loading;
    private GridView grvFood;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grid_food, container, false);
        searchFoodAdapter = new GridFoodAdapter(getActivity(), R.layout.item_search_food, new ArrayList<>());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindComponents(view);
        setEvents();
        bindViewModels();
    }

    private void bindComponents(@NonNull View view) {
        loading = view.findViewById(R.id.loading);
        grvFood = view.findViewById(R.id.grvFood);
        grvFood.setAdapter(searchFoodAdapter);
    }

    private void setEvents() {
        grvFood.setOnItemClickListener((adapterView, view1, i, l) -> {
            final FragmentActivity activity = getActivity();
            final Intent intent = new Intent(activity, FoodDetailsActivity.class);
            assert activity != null;
            intent.putExtra(BundleConstant.FOOD_ID, searchFoodAdapter.getItem(i).id);

            startActivity(intent);
        });
    }

    private void bindViewModels() {
        final FoodListViewModel foodListViewModel =
            new ViewModelProvider(requireActivity()).get(FoodListViewModel.class);
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
