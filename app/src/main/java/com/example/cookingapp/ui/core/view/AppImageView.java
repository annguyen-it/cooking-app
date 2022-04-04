package com.example.cookingapp.ui.core.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppImageView extends androidx.appcompat.widget.AppCompatImageView {
    private OnImageChangeListener onImageChangeListener;

    public AppImageView(Context context) {
        super(context);
    }

    public AppImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnImageChangeListener(OnImageChangeListener onImageChangeListener) {
        this.onImageChangeListener = onImageChangeListener;
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        if (onImageChangeListener != null) {
            onImageChangeListener.imageChangedInView(this);
        }
    }

    public interface OnImageChangeListener {
        void imageChangedInView(ImageView imageView);
    }
}
