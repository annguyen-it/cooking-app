package com.example.cookingapp.ui.discover;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.LoginDto;
import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.databinding.FragmentDiscoverBinding;
import com.example.cookingapp.service.http.AccountService;
import com.example.cookingapp.service.http.CountryService;
import com.example.cookingapp.service.http.HttpService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment {
    String[] name = {"A", "AB", "BC"};
    private FragmentDiscoverBinding binding;
    private static ConstraintLayout layout;
    private Spinner cboNuoc;
    private ArrayAdapter<CountryModel> adapterCountry;
    private DiscoverViewModel discoverViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        DiscoverViewModel discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
//        binding.lstSearch.setAdapter(arrayAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.layoutTimKiem);
        cboNuoc = view.findViewById(R.id.cboNuoc);

        discoverViewModel = new ViewModelProvider(requireActivity()).get(DiscoverViewModel.class);
        discoverViewModel.getCountry().observe(getViewLifecycleOwner(), new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                adapterCountry = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,countryModels);

                adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                cboNuoc.setAdapter(adapterCountry);
            }
        });
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

            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        showMenuSearch(layout);
                    }else {
                        hideMenuSearch(layout);
                    }
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
                    return false;
                }
            });

        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void showMenuSearch(View view){
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

    public void hideMenuSearch(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                -view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }



}
