package com.example.cookingapp.ui.fragment.discover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.SearchResponseModel;
import com.example.cookingapp.ui.activity.foodDetails.FoodDetailsActivity;
import com.example.cookingapp.ui.adapter.SearchFoodAdapter;
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.databinding.FragmentDiscoverBinding;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.activity.MainActivity;
import com.example.cookingapp.ui.core.adapter.SpinnerAdapter;
import com.example.cookingapp.util.constant.BundleConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment {
    private FragmentDiscoverBinding binding;
    private static ConstraintLayout layout;
    private Spinner cboCountry;
    private SpinnerAdapter<CountryModel> adapterCountry;
    public DiscoverViewModel discoverViewModel;
    private String txtSearch;
    private String countrySearch;
    private CheckBox isVegetarian;
    private Button btnFilter;
    private GridView grdFood;

    private SearchFoodAdapter searchFoodAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        searchFoodAdapter = new SearchFoodAdapter(getActivity(), R.layout.item_search_food, new ArrayList<>());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindComponents(view);
        addEventToComponents();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                hideMenuSearch(layout);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getMenuInflater().inflate(R.menu.menu, menu);
            MenuItem menuItem = menu.findItem(R.id.action_search);

            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Nhập từ khóa");
            searchView.setFocusable(true);

            searchView.setOnQueryTextFocusChangeListener((view, b) -> {
                if (b) {
                    showMenuSearch(layout);
                }
                else {
                    hideMenuSearch(layout);
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //arrayAdapter.getFilter().filter(s);
                    txtSearch = s;
                    Log.e("123", txtSearch);
                    return false;
                }
            });

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void bindComponents(@NonNull View view) {
        txtSearch = "";
        countrySearch = "";
        layout = view.findViewById(R.id.layoutTimKiem);
        cboCountry = view.findViewById(R.id.cboNuoc);
        isVegetarian = view.findViewById(R.id.ckbDoAnChay);
        btnFilter = view.findViewById(R.id.btnLoc);
        grdFood = view.findViewById(R.id.grvFood);
    }

    public void addEventToComponents() {
        btnFilter.setOnClickListener(v -> filter());

        discoverViewModel = new ViewModelProvider(requireActivity()).get(DiscoverViewModel.class);
        discoverViewModel.getCountry().observe(getViewLifecycleOwner(), countryModels -> {
            if (!countryModels.get(0).isDefaultValue()) {
                countryModels.add(0, new CountryModel().defaultValue());
            }
            adapterCountry = new SpinnerAdapter<>(requireActivity(), countryModels);
            cboCountry.setAdapter(adapterCountry);
        });

        cboCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countrySearch = adapterCountry.getItem(i).code;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        grdFood.setAdapter(searchFoodAdapter);
        grdFood.setOnItemClickListener((adapterView, view1, i, l) -> {
            final MainActivity activity = (MainActivity) getActivity();
            final Intent intent = new Intent(activity, FoodDetailsActivity.class);
            assert activity != null;
            intent.putExtra(BundleConstant.FOOD_ID, searchFoodAdapter.getItem(i).id);

            startActivity(intent);
        });
    }

    public void showMenuSearch(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
            0,                 // fromXDelta
            0,                 // toXDelta
            view.getHeight(),  // fromYDelta
            0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void hideMenuSearch(@NonNull View view) {
        TranslateAnimation animate = new TranslateAnimation(
            0,                  // fromXDelta
            0,                  // toXDelta
            0,                  // fromYDelta
            -view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    private void filter() {
        new HttpService<>((MainActivity) getActivity()).instance(FoodService.class)
            .searchFood(txtSearch, isVegetarian.isChecked(), countrySearch)
            .enqueue(new Callback<SearchResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<SearchResponseModel> call, @NonNull Response<SearchResponseModel> response) {
                    if (!response.isSuccessful() || response.body() == null) {
                        return;
                    }

                    List<FoodModel> lstFood = response.body().foods;
                    searchFoodAdapter.setListFood(lstFood);
                }

                @Override
                public void onFailure(@NonNull Call<SearchResponseModel> call, @NonNull Throwable t) { }
            });
    }
}
