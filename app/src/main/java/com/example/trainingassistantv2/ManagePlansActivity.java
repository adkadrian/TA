package com.example.trainingassistantv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ManagePlansActivity extends AppCompatActivity {

    TabLayout tabLayout;
    private Context context = this;

    ArrayList<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_plans);

        initLayout();
        DatabaseHelper db = new DatabaseHelper(this);
        groups = db.getAllMuscleGroups();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        // Lookup the recyclerview in activity layout
                        RecyclerView rvExercises = findViewById(R.id.rvGroups);

                        // Create adapter passing in the sample user data
                        ExercisesAdapter adapter = new ExercisesAdapter(context, groups);
                        // Attach the adapter to the recyclerview to populate items
                        rvExercises.setAdapter(adapter);
                        // Set layout manager to position the items
                        rvExercises.setLayoutManager(new LinearLayoutManager(ManagePlansActivity.this));
                        // That's all!
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void initLayout() {
        tabLayout = findViewById(R.id.tabLayout);
    }
}