package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.Step;

import java.util.List;

public class AddStepAdapter extends RecyclerView.Adapter<AddStepAdapter.ViewHolder> {
    private final List<Step> steps;

    public AddStepAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public AddStepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_add_step, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Step step = steps.get(position);

        holder.txtStepName.setText(step.getName());
        holder.txtStepDescription.setText(step.getDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtStepName;
        public final TextView txtStepDescription;
        public final Button btnStepAddImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStepName = itemView.findViewById(R.id.txtStepName);
            txtStepDescription = itemView.findViewById(R.id.txtStepDescription);
            btnStepAddImage = itemView.findViewById(R.id.btnStepAddImage);
        }
    }
}
