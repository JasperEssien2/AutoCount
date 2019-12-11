package com.example.autocount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autocount.databinding.ItemCategoryBinding;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private ItemCategoryBinding binding;
    private List<ButtonModel> buttonModels;

    public CategoriesAdapter(List<ButtonModel> buttonModels) {
        super();
        this.buttonModels = buttonModels;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_category, parent, false);
        return new CategoriesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesViewHolder holder, final int position) {
        final ButtonModel buttonModel = buttonModels.get(holder.getAdapterPosition());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonModel.setCurrentCount(buttonModel.getCurrentCount() + 1);
                buttonModels.set(position, buttonModel);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonModel.getCurrentCount() != 0)
                    buttonModel.setCurrentCount(buttonModel.getCurrentCount() - 1);
                buttonModels.set(position, buttonModel);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.currentCount.setText(String.valueOf(buttonModel.getCurrentCount()));
        holder.name.setText(buttonModel.getName().substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return buttonModels.size();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView currentCount;
        private ImageButton minus, add;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            currentCount = binding.count;
            minus = binding.minus;
            add = binding.add;
        }
    }
}
