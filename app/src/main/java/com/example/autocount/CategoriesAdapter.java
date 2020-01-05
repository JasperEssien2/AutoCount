package com.example.autocount;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autocount.databinding.ItemCategoryBinding;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private ItemCategoryBinding binding;
    private ButtonDatabase db;
    private List<ButtonModel> buttonModels;
    private InsertCountAsyncTask insertCountAsyncTask;

    public CategoriesAdapter(ButtonDatabase db, List<ButtonModel> buttonModels) {
        super();
        this.db = db;
        this.buttonModels = buttonModels;
        insertCountAsyncTask = new InsertCountAsyncTask(db);
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
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), buttonModel.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonModel.setTotalCount(buttonModel.getTotalCount() + 1);
                buttonModels.set(position, buttonModel);
                notifyItemChanged(holder.getAdapterPosition());
                new InsertCountAsyncTask(db).execute(buttonModel);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonModel.getTotalCount() != 0)
                    buttonModel.setTotalCount(buttonModel.getTotalCount() - 1);
                buttonModels.set(position, buttonModel);
                notifyItemChanged(holder.getAdapterPosition());
                new InsertCountAsyncTask(db).execute(buttonModel);
            }
        });
        holder.currentCount.setText(String.valueOf(buttonModel.getTotalCount()));
        holder.name.setText(getNameInitials(buttonModel));
    }

    private String getNameInitials(ButtonModel buttonModel) {
        String[] s = buttonModel.getName().split(" ");
        return s.length >= 2 ? s[0].substring(0, 1).toUpperCase() + s[1].substring(0, 1).toUpperCase() : buttonModel.getName().substring(0, 1);
    }

    @Override
    public int getItemCount() {
        return buttonModels.size();
    }

    public void set(List<ButtonModel> buttonModels) {
        this.buttonModels = buttonModels;
        notifyDataSetChanged();
    }

    public static class InsertCountAsyncTask extends AsyncTask<ButtonModel, Void, Void> {
        private ButtonDatabase db;

        public InsertCountAsyncTask(ButtonDatabase db) {

            this.db = db;
        }

        @Override
        protected Void doInBackground(ButtonModel... buttonModels) {
            db.buttonModelDao().update(buttonModels[0]);
            return null;
        }
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView currentCount;
        private ImageButton minus, add;
        private CircleImageView imageView;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            currentCount = binding.count;
            minus = binding.minus;
            add = binding.add;
            imageView = binding.cg;
            imageView.setImageResource(R.color.colorBlack);
        }
    }
}
