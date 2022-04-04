package com.example.cookingapp.ui.fragment.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
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
import com.example.cookingapp.databinding.FragmentDiscoverBinding;
import com.example.cookingapp.ui.adapter.SpinnerAdapter;

public class DiscoverFragment extends Fragment {
    //String[] name = {"A", "AB", "BC"};
    private FragmentDiscoverBinding binding;
    private static ConstraintLayout layout;
    private Spinner cboNuoc;
    private SpinnerAdapter<CountryModel> adapterCountry;
    public DiscoverViewModel discoverViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        DiscoverViewModel discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
//        binding.lstSearch.setAdapter(arrayAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.layoutTimKiem);
        cboNuoc = view.findViewById(R.id.cboNuoc);

        discoverViewModel = new ViewModelProvider(requireActivity()).get(DiscoverViewModel.class);
        discoverViewModel.getCountry().observe(getViewLifecycleOwner(), countryModels -> {
            if (!countryModels.get(0).code.isEmpty()) {
                countryModels.add(0, new CountryModel().defaultValue());
            }
            adapterCountry = new SpinnerAdapter<>(requireActivity(), countryModels);

            cboNuoc.setAdapter(adapterCountry);
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
                    return false;
                }
            });

        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void showMenuSearch(View view) {
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

    public void hideMenuSearch(View view) {
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
