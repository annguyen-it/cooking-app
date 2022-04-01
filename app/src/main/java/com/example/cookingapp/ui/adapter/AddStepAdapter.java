package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.ui.StepUiModel;
import com.example.cookingapp.ui.addFood.AddFoodActivity;

import java.util.List;

public class AddStepAdapter extends RecyclerView.Adapter<AddStepAdapter.ViewHolder> {
    private final List<StepUiModel> steps;
    private final AddFoodActivity activity;
    private Context context;

    public AddStepAdapter(AddFoodActivity activity, List<StepUiModel> steps) {
        this.steps = steps;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AddStepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_add_step, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TextView txtStepNumber = holder.txtStepNumber;
        final Button btnStepAddImage = holder.btnStepAddImage;
        final ImageView imgStep = holder.imgStep;
        final Button btnStepRemove = holder.btnStepRemove;

        txtStepNumber.setText(context.getString(R.string.txt_step, position + 1));
        btnStepAddImage.setOnClickListener(view -> activity.uploadImage(btnStepAddImage, imgStep));
        btnStepRemove.setOnClickListener(view -> {
            steps.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, steps.size() - position);
        });
        imgStep.setOnClickListener(view -> activity.uploadImage(btnStepAddImage, imgStep));
        imgStep.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void addNewStep() {
        final int itemsCount = getItemCount();
        steps.add(new StepUiModel());
        notifyItemInserted(itemsCount);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtStepNumber;
        public final EditText txtStepName;
        public final EditText txtStepDescription;
        public final Button btnStepAddImage;
        public final Button btnStepRemove;
        public final ImageView imgStep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStepNumber = itemView.findViewById(R.id.txtStepNumber);
            txtStepName = itemView.findViewById(R.id.txtStepName);
            txtStepDescription = itemView.findViewById(R.id.txtStepDescription);
            btnStepAddImage = itemView.findViewById(R.id.btnStepAddImage);
            btnStepRemove = itemView.findViewById(R.id.btnStepRemove);
            imgStep = itemView.findViewById(R.id.imgStepUploadFood);
        }
    }
}
