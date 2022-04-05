package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.StepModel;
import com.example.cookingapp.util.helper.UiHelper;

import java.util.List;

public class StepAdapter<A extends AppCompatActivity> extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private final List<StepModel> steps;
    private final A activity;
    private Context context;

    public StepAdapter(List<StepModel> steps, A activity) {
        this.steps = steps;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_step, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TextView txtDetailsStepName = holder.txtDetailsStepName;
        final TextView txtStepName = holder.txtStepName;
        final ImageView imgStep = holder.imgStep;
        final StepModel step = steps.get(position);

        final SpannableStringBuilder stepName =
            new SpannableStringBuilder(context.getString(R.string.txt_details_step, step.stepNumber + 1, step.name));
        stepName.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stepName.setSpan(new UnderlineSpan(), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtDetailsStepName.setText(stepName);
        txtStepName.setText(step.description);

        UiHelper.setImageBitmapAsync(imgStep, step.images.name, activity);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void addSteps(List<StepModel> steps) {
        final int count = getItemCount();
        this.steps.addAll(steps);
        notifyItemRangeInserted(count, steps.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtDetailsStepName;
        public final TextView txtStepName;
        public final ImageView imgStep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDetailsStepName = itemView.findViewById(R.id.txtStepName);
            txtStepName = itemView.findViewById(R.id.txtStepDescription);
            imgStep = itemView.findViewById(R.id.imgStep);
        }
    }
}
