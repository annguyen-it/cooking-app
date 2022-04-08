package com.example.cookingapp.ui.fragment.discover;

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
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.data.model.SearchResponseModel;
import com.example.cookingapp.databinding.FragmentDiscoverBinding;
import com.example.cookingapp.service.http.FoodService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.activity.MainActivity;
import com.example.cookingapp.ui.core.adapter.SpinnerAdapter;
import com.example.cookingapp.ui.core.viewModel.FoodListViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment {
    private FragmentDiscoverBinding binding;
    private ConstraintLayout layout;

    private DiscoverViewModel discoverViewModel;
    private FoodListViewModel foodListViewModel;
    private SpinnerAdapter<CountryModel> adapterCountry;

    private Spinner cboCountry;
    private String txtSearch;
    private String countrySearch;
    private CheckBox isVegetarian;
    private Button btnFilter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindComponents(view);
        bindViewModels();
        addEventToComponents();
        observeViewModels();
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

    private void bindComponents(@NonNull View view) {
        txtSearch = "";
        countrySearch = "";
        
        layout = view.findViewById(R.id.layoutTimKiem);
        cboCountry = view.findViewById(R.id.cboNuoc);
        isVegetarian = view.findViewById(R.id.ckbDoAnChay);
        btnFilter = view.findViewById(R.id.btnLoc);
    }

    private void bindViewModels() {
        discoverViewModel = new ViewModelProvider(requireActivity()).get(DiscoverViewModel.class);
        foodListViewModel = new ViewModelProvider(requireActivity()).get(FoodListViewModel.class);
//        foodListViewModel.setLoading(false);
    }

    private void addEventToComponents() {
        btnFilter.setOnClickListener(v -> filter());
        cboCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countrySearch = adapterCountry.getItem(i).code;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }
    
    private void observeViewModels() {
        discoverViewModel.getCountries().observe(getViewLifecycleOwner(), countryModels -> {
            if (!countryModels.get(0).isDefaultValue()) {
                countryModels.add(0, new CountryModel().defaultValue());
            }
            adapterCountry = new SpinnerAdapter<>(requireActivity(), countryModels);
            cboCountry.setAdapter(adapterCountry);
        });
    }

    private void showMenuSearch(@NonNull View view) {
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

    private void hideMenuSearch(@NonNull View view) {
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
        foodListViewModel.setLoading(true);

        new HttpService<>((MainActivity) getActivity()).instance(FoodService.class)
            .searchFood(txtSearch, isVegetarian.isChecked(), countrySearch)
            .enqueue(new Callback<SearchResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<SearchResponseModel> call, @NonNull Response<SearchResponseModel> response) {
                    if (!response.isSuccessful() || response.body() == null) {
                        return;
                    }

                    SearchResponseModel responseModel = response.body();
                    foodListViewModel.setFoods(responseModel.foods);
                    foodListViewModel.setLoading(false);
                }

                @Override
                public void onFailure(@NonNull Call<SearchResponseModel> call, @NonNull Throwable t) { }
            });
    }
}
