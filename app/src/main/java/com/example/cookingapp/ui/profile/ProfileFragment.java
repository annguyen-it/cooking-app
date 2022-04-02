package com.example.cookingapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingapp.R;
import com.example.cookingapp.ui.addFood.AddFoodActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView imgAvatar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Binding components
        FloatingActionButton btnAddFood = view.findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding(view);

    }

    private void binding(View view){
        imgAvatar = view.findViewById(R.id.imgAvatar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAddFood:
                startActivity(new Intent(getActivity(), AddFoodActivity.class));
                break;
            case R.id.imgAvatar:

                break;
        }
    }
}
