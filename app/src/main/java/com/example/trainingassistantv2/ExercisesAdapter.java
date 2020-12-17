package com.example.trainingassistantv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private final ArrayList<Group> groups;
    private final Context context;

    @Override
    public ExercisesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View exercisesView = inflater.inflate(R.layout.groups, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(exercisesView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Group group = groups.get(position);
        TextView txtGroupName = holder.txtGroupName;

        txtGroupName.setText(group.getName());
        RecyclerView rvExercisesList = holder.rvExercisesList;
        ImageView imgArrowDown = holder.imgArrowDown;
        ImageView imgArrowUp = holder.imgArrowUp;
        int muscleGroupId = group.getId();
        DatabaseHelper db = new DatabaseHelper(context);

        ArrayList<Exercise> exercises = db.getAllExercisesByMuscle(muscleGroupId);

        ExerciseRowAdapter rowAdapter = new ExerciseRowAdapter(context, exercises, muscleGroupId);
        rvExercisesList.setAdapter(rowAdapter);
        rvExercisesList.setLayoutManager(new LinearLayoutManager(txtGroupName.getContext()));

        imgArrowDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rvExercisesList.setVisibility(View.VISIBLE);
                imgArrowDown.setVisibility(View.GONE);
                imgArrowUp.setVisibility(View.VISIBLE);
            }
        });

        imgArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvExercisesList.setVisibility(View.GONE);
                imgArrowDown.setVisibility(View.VISIBLE);
                imgArrowUp.setVisibility(View.GONE);
            }
        });

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return groups.size();
    }

    // Pass in the contact array into the constructor
    public ExercisesAdapter(Context pContext, ArrayList<Group> groups) {
        context = pContext;
        this.groups = groups;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtGroupName;
        private final RecyclerView rvExercisesList;
        private final ImageView imgArrowDown, imgArrowUp;
        //, imgDelete, imgEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            rvExercisesList = itemView.findViewById(R.id.rvExercisesList);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            imgArrowDown = itemView.findViewById(R.id.arrow_down);
            imgArrowUp = itemView.findViewById(R.id.arrow_up);
//            imgDelete = itemView.findViewById(R.id.delete);
//            imgEdit = itemView.findViewById(R.id.edit);

        }
    }

}
