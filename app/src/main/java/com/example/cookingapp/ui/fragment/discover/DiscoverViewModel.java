package com.example.cookingapp.ui.fragment.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookingapp.data.model.CountryModel;

import java.util.List;


public class DiscoverViewModel extends ViewModel {
    private final MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();

    public LiveData<List<CountryModel>> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryModel> countriesList) {
        countries.setValue(countriesList);
    }
}
