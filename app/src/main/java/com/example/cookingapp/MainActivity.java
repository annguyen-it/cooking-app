package com.example.cookingapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.databinding.ActivityMainBinding;
import com.example.cookingapp.service.http.CountryService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.discover.DiscoverViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public List<CountryModel> countryList;
    public DiscoverViewModel discoverViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        countryList = new ArrayList<>();
        discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        getCountry();
    }

    public void getCountry() {
        Callback<List<CountryModel>> callback = new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                countryList = response.body();
                getModelCountry(countryList);
            }

            @Override
            public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable ts) {

            }
        };
        new HttpService<>(this).instance(CountryService.class)
            .getCountries()
            .enqueue(callback);


//        Log.e("1235",discoverViewModel.getCountry().+"");
    }

    private void getModelCountry(List<CountryModel> countryList) {
        discoverViewModel.setCountry(countryList);
    }
}
