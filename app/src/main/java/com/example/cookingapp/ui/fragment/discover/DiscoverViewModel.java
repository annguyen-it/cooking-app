package com.example.cookingapp.ui.fragment.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookingapp.data.model.CountryModel;

import java.util.List;


public class DiscoverViewModel extends ViewModel {

    private final MutableLiveData<List<CountryModel>> country;

    public DiscoverViewModel() {
        country = new MutableLiveData<>();
    }

    public LiveData<List<CountryModel>> getCountry(){
        return country;
    }

    public void setCountry(List<CountryModel> lstCountry){
        country.setValue(lstCountry);
    }
}
