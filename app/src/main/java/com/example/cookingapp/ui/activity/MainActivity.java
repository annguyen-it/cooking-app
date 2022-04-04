package com.example.cookingapp.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.databinding.ActivityMainBinding;
import com.example.cookingapp.service.http.CountryService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.fragment.discover.DiscoverViewModel;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DiscoverViewModel discoverViewModel;
    private ArrayList<CountryModel> countryList;

    public ArrayList<CountryModel> getCountryList() {
        return countryList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_dashboard)
            .build();
        NavHostFragment navHostFragment =
            (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        getCountry();
    }

    public void getCountry() {
        Callback<ArrayList<CountryModel>> callback = new Callback<ArrayList<CountryModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryModel>> call,
                                   Response<ArrayList<CountryModel>> response) {
                countryList = response.body();
                getModelCountry(countryList);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryModel>> call, @NonNull Throwable t) { }
        };
        new HttpService<>(this).instance(CountryService.class)
            .getCountries()
            .enqueue(callback);
    }

    private void getModelCountry(List<CountryModel> countryList) {
        discoverViewModel.setCountry(countryList);
    }
}
