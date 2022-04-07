package com.example.cookingapp.ui.core.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.core.HasDefaultValue;

import java.util.List;

public class SpinnerAdapter<T extends HasDefaultValue<T>> extends ArrayAdapter<T> {
    public SpinnerAdapter(@NonNull Context context, @NonNull List<T> objects) {
        super(context, R.layout.support_simple_spinner_dropdown_item, objects);
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        ((TextView) view).setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
        return super.getDropDownView(position, convertView, parent);
    }
}
