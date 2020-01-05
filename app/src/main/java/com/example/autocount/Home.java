package com.example.autocount;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.Room;

import com.example.autocount.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Home extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ButtonDatabase db;
    private CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                ButtonDatabase.class, ButtonDatabase.DB_NAME).build();
        setUpAdapters();
        try {
            new GetAutoCountAsyncTask(db).execute().get()
                    .observe(this, new Observer<List<ButtonModel>>() {
                        @Override
                        public void onChanged(List<ButtonModel> buttonModels) {
                            if (buttonModels == null || buttonModels.isEmpty()) {
                                binding.progressCircular.setVisibility(View.VISIBLE);
                                new InitCountAsyncTask(db, binding.progressCircular).execute(getListOfButtons());
                            }
                            adapter.set(buttonModels);
                        }
                    });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.total:
                startActivity(new Intent(this, Total.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpAdapters(){
        adapter = new CategoriesAdapter(db, new ArrayList<ButtonModel>());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private List<ButtonModel> getListOfButtons() {
        List<ButtonModel> models = new ArrayList<>();
        int count = 0;
        for(String s : getResources().getStringArray(R.array.Categories)){
            ButtonModel buttonModel = new ButtonModel(s, 0, 0);
            buttonModel.setId(count);
            models.add(buttonModel);
            count++;
        }
        return models;
    }

    public static class GetAutoCountAsyncTask extends AsyncTask<Void, Void, LiveData<List<ButtonModel>>> {
        private final ButtonDatabase db;

        public GetAutoCountAsyncTask(ButtonDatabase db) {

            this.db = db;
        }

        @Override
        protected LiveData<List<ButtonModel>> doInBackground(Void... url) {
            return db.buttonModelDao().getAutoCountList();
        }
    }

    public static class InitCountAsyncTask extends AsyncTask<List<ButtonModel>, Void, Void> {
        private ButtonDatabase db;
        private ProgressBar progressBar;

        public InitCountAsyncTask(ButtonDatabase db, ProgressBar progressBar) {

            this.db = db;
            this.progressBar = progressBar;
        }

        @Override
        protected Void doInBackground(List<ButtonModel>... buttonModels) {
            db.buttonModelDao().insert(buttonModels[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
