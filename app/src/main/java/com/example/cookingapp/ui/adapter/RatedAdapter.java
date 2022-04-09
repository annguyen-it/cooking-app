package com.example.cookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.RateModel;
import com.example.cookingapp.util.helper.DateHelper;

import java.util.List;

public class RatedAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private List<RateModel> ratedModels;

    public RatedAdapter(Context context, int layout, List<RateModel> ratedModels) {
        this.context = context;
        this.layout = layout;
        this.ratedModels = ratedModels;
    }

    public void setListRated(List<RateModel> ratedModels) {
        this.ratedModels = ratedModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ratedModels.size();
    }

    @Override
    public Object getItem(int i) {
        return ratedModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            return null;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view != null) {
            view = inflater.inflate(layout, null);
        }

        //ImageView img = view.findViewById(R.id.img);
        TextView txtUserNameRated = view.findViewById(R.id.txtNameUserRated);
        RatingBar rated = view.findViewById(R.id.rtbRated);
        TextView txtComment = view.findViewById(R.id.txtCmt);
        TextView txtTimeCreated = view.findViewById(R.id.txtTimeCreated);

        RateModel rate = ratedModels.get(i);

        txtUserNameRated.setText(rate.owner.fullName);
        rated.setRating(rate.rate);
        txtComment.setText(rate.comment);
        txtTimeCreated.setText(DateHelper.format(rate.timeCreated));

        return view;
    }
}
