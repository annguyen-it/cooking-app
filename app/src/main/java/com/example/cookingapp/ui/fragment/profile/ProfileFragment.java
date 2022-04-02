package com.example.cookingapp.ui.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.UserModel;
import com.example.cookingapp.ui.activity.addFood.AddFoodActivity;
import com.example.cookingapp.util.constant.BundleConstant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final Intent activityIntent = requireActivity().getIntent();

        // Binding components
        final TextView txtName = view.findViewById(R.id.txtName);
        final ImageView imgAvatar = view.findViewById(R.id.imgAvatar);
        final FloatingActionButton btnAddFood = view.findViewById(R.id.btnAddFood);

        final UserModel userModel =
            new Gson().fromJson(activityIntent.getStringExtra(BundleConstant.ACCOUNT), UserModel.class);
        btnAddFood.setOnClickListener(this);
        txtName.setText(userModel.getFullName());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.btnAddFood) {
            startActivity(new Intent(getActivity(), AddFoodActivity.class));
        }
    }
}
