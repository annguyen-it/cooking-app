package com.example.cookingapp.ui.fragment.gridFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookingapp.R;
import com.example.cookingapp.ui.activity.foodDetails.FoodDetailsActivity;
import com.example.cookingapp.ui.adapter.GridFoodAdapter;
import com.example.cookingapp.ui.core.viewModel.FoodListViewModel;
import com.example.cookingapp.util.constant.BundleConstant;

import java.util.ArrayList;

public class GridFoodFragment extends Fragment {
    private GridFoodAdapter searchFoodAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grid_food, container, false);
        searchFoodAdapter = new GridFoodAdapter(getActivity(), R.layout.item_search_food, new ArrayList<>(), (AppCompatActivity) getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Components
        GridView grvFood = view.findViewById(R.id.grvFood);
        grvFood.setAdapter(searchFoodAdapter);
        grvFood.setOnItemClickListener((adapterView, view1, i, l) -> {
            final FragmentActivity activity = getActivity();
            final Intent intent = new Intent(activity, FoodDetailsActivity.class);
            assert activity != null;
            intent.putExtra(BundleConstant.FOOD_ID, searchFoodAdapter.getItem(i).id);

            startActivity(intent);
        });

        // View models
        FoodListViewModel foodListViewModel = new ViewModelProvider(requireActivity()).get(FoodListViewModel.class);
        foodListViewModel.getFoods()
            .observe(getViewLifecycleOwner(), foodModels -> searchFoodAdapter.setListFood(foodModels));
    }
}
