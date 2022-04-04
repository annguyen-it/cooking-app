package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.ui.StepUiModel;
import com.example.cookingapp.ui.activity.addFood.AddFoodActivity;
import com.example.cookingapp.ui.core.event.AppTextWatcher;
import com.example.cookingapp.ui.core.view.AppImageView;
import com.example.cookingapp.util.helper.UiHelper;

import java.util.List;

public class AddStepAdapter extends RecyclerView.Adapter<AddStepAdapter.ViewHolder> {
    private final List<StepUiModel> steps;
    private final AddFoodActivity activity;
    private Context context;

    public List<StepUiModel> getSteps() {
        return steps;
    }

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
        final TextView txtStepName = holder.txtStepName;
        final TextView txtStepDescription = holder.txtStepDescription;
        final Button btnStepAddImage = holder.btnStepAddImage;
        final AppImageView imgStep = holder.imgStep;
        final Button btnStepRemove = holder.btnStepRemove;

        txtStepNumber.setText(context.getString(R.string.txt_step, position + 1));
        btnStepAddImage.setOnClickListener(view -> activity.changeImage(btnStepAddImage, imgStep));
        btnStepRemove.setOnClickListener(view -> {
            final int currentPosition = holder.getAdapterPosition();
            steps.remove(currentPosition);
            notifyItemRemoved(currentPosition);
            notifyItemRangeChanged(currentPosition, steps.size() - currentPosition);
        });
        txtStepName.addTextChangedListener(new AppTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence) {
                final int position = holder.getAdapterPosition();
                steps.get(position).setName(charSequence.toString());
            }
        });
        txtStepDescription.addTextChangedListener(new AppTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence) {
                final int position = holder.getAdapterPosition();
                steps.get(position).setDescription(charSequence.toString());
            }
        });
        imgStep.setOnClickListener(view -> activity.changeImage(btnStepAddImage, imgStep));
        imgStep.setOnImageChangeListener(view -> {
            final int p = holder.getAdapterPosition();
            final Uri uri = UiHelper.getUri(activity.getApplicationContext(), view.getId());
            steps.get(p).setImageUri(uri);
        });
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
        public final AppImageView imgStep;

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
