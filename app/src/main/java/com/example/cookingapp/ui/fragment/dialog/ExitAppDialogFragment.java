package com.example.cookingapp.ui.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ExitAppDialogFragment extends DialogFragment {
    private final Activity activity;

    public ExitAppDialogFragment(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc chắn muốn thoát không?")
            .setPositiveButton("Thoát ứng dụng", (dialogInterface, i) -> activity.finish())
            .setNegativeButton("Ở lại", ((dialogInterface, i) -> { }));
        return builder.create();
    }
}
