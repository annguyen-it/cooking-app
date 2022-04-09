package com.example.cookingapp.ui.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.UserModel;
import com.example.cookingapp.ui.activity.MainActivity;
import com.example.cookingapp.ui.activity.addFood.AddFoodActivity;
import com.example.cookingapp.ui.activity.foodList.MyFoodListActivity;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.helper.ObjectHelper;
import com.example.cookingapp.util.helper.UiHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    String accountExtra;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final Intent activityIntent = requireActivity().getIntent();
        accountExtra = activityIntent.getStringExtra(BundleConstant.ACCOUNT);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Binding components
        final TextView txtName = view.findViewById(R.id.txtName);
        final ImageView imgAvatar = view.findViewById(R.id.imgAvatar);
        final FloatingActionButton btnAddFood = view.findViewById(R.id.btnAddFood);
        final Button btnMyFood = view.findViewById(R.id.btnMyFood);

        // Add events
        final UserModel userModel = ObjectHelper.fromJson(accountExtra, UserModel.class);
        btnAddFood.setOnClickListener(this);
        btnMyFood.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        txtName.setText(userModel.fullName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(@NonNull View view) {
        final int id = view.getId();
        if (id == R.id.btnAddFood) {
            final MainActivity activity = (MainActivity) getActivity();
            final Intent intent = new Intent(activity, AddFoodActivity.class);
            assert activity != null;
            intent.putParcelableArrayListExtra(BundleConstant.COUNTRY, activity.getCountryList());

            startActivity(intent);
        }
        else if (id == R.id.btnMyFood) {
            final MainActivity activity = (MainActivity) getActivity();
            final Intent intent = new Intent(activity, MyFoodListActivity.class);
            startActivity(intent);
        }
    }
}
