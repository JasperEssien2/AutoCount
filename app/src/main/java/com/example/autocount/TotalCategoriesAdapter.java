package com.example.autocount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autocount.databinding.ItemTotalCategoryBinding;

import java.util.List;

public class TotalCategoriesAdapter extends RecyclerView.Adapter<TotalCategoriesAdapter.CategoriesViewHolder> {
    private ItemTotalCategoryBinding binding;
    private ButtonDatabase db;
    private List<ButtonModel> buttonModels;

    public TotalCategoriesAdapter(ButtonDatabase db, List<ButtonModel> buttonModels) {
        super();
        this.db = db;
        this.buttonModels = buttonModels;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_total_category, parent, false);
        return new CategoriesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesViewHolder holder, final int position) {
        final ButtonModel buttonModel = buttonModels.get(holder.getAdapterPosition());
        holder.currentCount.setText(String.valueOf(buttonModel.getTotalCount()));
        holder.name.setText(buttonModel.getName());
    }


    @Override
    public int getItemCount() {
        return buttonModels.size();
    }

    public void set(List<ButtonModel> buttonModels) {
        this.buttonModels = buttonModels;
        notifyDataSetChanged();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView currentCount;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            currentCount = binding.total;
        }
    }

}
