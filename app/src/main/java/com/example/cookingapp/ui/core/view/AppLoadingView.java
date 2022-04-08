package com.example.cookingapp.ui.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cookingapp.R;
import com.example.cookingapp.util.helper.UiHelper;

public class AppLoadingView extends ConstraintLayout {
    public AppLoadingView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public AppLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AppLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(@NonNull Context context) {
        View.inflate(context, R.layout.view_loading, this);
        setElevation(1);
        setBackgroundResource(R.color.white);
    }

    public void fadeIn() {
        UiHelper.fade(this, 0, false);
    }

    public void fadeOut() {
        UiHelper.fade(this, 600);
    }
}
