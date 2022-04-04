package com.example.cookingapp.ui.core.event;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class AppTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onTextChanged(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    public abstract void onTextChanged(CharSequence charSequence);
}
