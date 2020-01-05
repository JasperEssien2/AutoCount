package com.example.autocount;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.autocount.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Total extends AppCompatActivity {

    ActivityMainBinding binding;
    private ButtonDatabase db;
    private TotalCategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Total");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                ButtonDatabase.class, ButtonDatabase.DB_NAME).build();
        setUpAdapters();
        try {
            new Home.GetAutoCountAsyncTask(db).execute().get()
                    .observe(this, new Observer<List<ButtonModel>>() {
                        @Override
                        public void onChanged(List<ButtonModel> buttonModels) {
                            adapter.set(buttonModels);
                        }
                    });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUpAdapters() {
        adapter = new TotalCategoriesAdapter(db, new ArrayList<ButtonModel>());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}
