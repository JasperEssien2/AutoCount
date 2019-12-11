package com.example.autocount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.autocount.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpAdapters();
    }

    private void setUpAdapters(){
        binding.recyclerView.setAdapter(new CategoriesAdapter(getListOfButtons()));
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
    }

    private List<ButtonModel> getListOfButtons() {
        List<ButtonModel> models = new ArrayList<>();
        for(String s : getResources().getStringArray(R.array.Categories)){
            models.add(new ButtonModel(s, 0, 0));
        }
        return models;
    }
}
