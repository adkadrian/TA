package com.example.trainingassistantv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class ExerciseRowAdapter extends RecyclerView.Adapter<ExerciseRowAdapter.ViewHolder> {

    private ArrayList<Exercise> exercises;
    private final Context context;
    private CardView cvNewExercise, buttonAccept;
    private Spinner spiTools, spiMuscles, spiCategories;
    private final DatabaseHelper db;
    private Map<String, Integer> tools, muscles, categories;
    private ArrayList<String> musclesNames, toolsNames, categoriesNames;
    private TextView editExerciseName;
    private Exercise newExercise;
    private Exercise editingExercise;
    private int editingExercisePosition;
    private Boolean isNew = true;

    @Override
    public ExerciseRowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View exerciseView = inflater.inflate(R.layout.group_row, parent, false);

        // Return a new holder instance
        ExerciseRowAdapter.ViewHolder viewHolder = new ExerciseRowAdapter.ViewHolder(exerciseView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ExerciseRowAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Exercise exercise = exercises.get(position);

        // Set item views based on your views and data model
        TextView txtExerciseName = holder.txtExerciseName;
        ImageView imgDelete = holder.imgDelete;
        ImageView imgEdit = holder.imgEdit;
        ImageView imgAdd = holder.imgAdd;

        txtExerciseName.setText(exercise.getName());

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseName = exercise.getName();
                String exerciseTool = exercise.getTool();
                int spiToolPosition = toolsNames.indexOf(exerciseTool);

                String exerciseMuscle = exercise.getMuscle();
                int spiMusclePosition = musclesNames.indexOf(exerciseMuscle);

                String exerciseCategory = exercise.getCategory();
                int spiCategoryPosition = categoriesNames.indexOf(exerciseCategory);

                spiTools.setSelection(spiToolPosition);
                spiMuscles.setSelection(spiMusclePosition);
                spiCategories.setSelection(spiCategoryPosition);
                editExerciseName.setText(exerciseName);
                cvNewExercise.setVisibility(View.VISIBLE);

                newExercise = exercise;
                editingExercisePosition = position;
                editingExercise = exercise;
                isNew = false;
            }
        });

        if (exercise.getId() == -1) {
            imgDelete.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            imgAdd.setVisibility(View.VISIBLE);
        }

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseMuscle = exercise.getMuscle();
                int spiMusclePosition = musclesNames.indexOf(exerciseMuscle);

                spiMuscles.setSelection(spiMusclePosition);

                editExerciseName.setText("");
                editExerciseName.setHint("Here enter exercise name");
                cvNewExercise.setVisibility(View.VISIBLE);
                isNew = true;
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Do you want to delete " + exercise.getName() + " ?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteExerciseByID(exercise.getId());
                        exercises.remove(exercise);
                        notifyItemRemoved(position);
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create().show();
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(isNew.toString());

                String exerciseName = editExerciseName.getText().toString();

                String exerciseTool = spiTools.getSelectedItem().toString();
                int exerciseToolID = tools.get(exerciseTool);

                String exerciseMuscle = spiMuscles.getSelectedItem().toString();
                int exerciseMuscleID = muscles.get(exerciseMuscle);

                String exerciseCategory = spiCategories.getSelectedItem().toString();
                int exerciseCategoryID = categories.get(exerciseCategory);

                if (isNew) {
                    newExercise = new Exercise(exerciseName, exerciseTool, exerciseMuscle, exerciseCategory);
                } else {
                    int exerciseID = editingExercise.getId();
                    newExercise = new Exercise(exerciseID, exerciseName, exerciseTool, exerciseMuscle, exerciseCategory);
                }

                if (!newExercise.equals(editingExercise) && !isNew) {
                    try {
                        db.updateExerciseByID(newExercise.getId(), exerciseName, exerciseToolID, exerciseMuscleID, exerciseCategoryID);
                        exercises.set(exercises.indexOf(editingExercise), newExercise);
                        notifyItemChanged(editingExercisePosition);
                        cvNewExercise.setVisibility(View.GONE);
                        isNew = false;
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else if (isNew) {
                    try {
                        db.insertNewExercise(newExercise);
                        exercises.add(newExercise);
                        notifyDataSetChanged();
                        cvNewExercise.setVisibility(View.GONE);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    cvNewExercise.setVisibility(View.GONE);
                }
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return exercises.size();
    }

    // Pass in the contact array into the constructor
    public ExerciseRowAdapter(Context pContext, ArrayList<Exercise> allExercises, int groupId) {
        this.context = pContext;
        this.db = new DatabaseHelper(context);
        this.exercises = allExercises;
        String muscleGroup = db.getMuscleNameByID(groupId);
        this.exercises.add(new Exercise(-1, "Add exercise", null, muscleGroup, null ));

        initUI();
        initData();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtExerciseName;
        private final ImageView imgDelete, imgEdit, imgAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtExerciseName);
            imgDelete = itemView.findViewById(R.id.delete);
            imgEdit = itemView.findViewById(R.id.edit);
            imgAdd = itemView.findViewById(R.id.add);
        }
    }

    public void initUI() {
        Activity activity = (Activity) context;

        spiTools = activity.findViewById(R.id.spiTools);
        spiMuscles = activity.findViewById(R.id.spiMuscles);
        spiCategories = activity.findViewById(R.id.spiCategories);

        cvNewExercise = activity.findViewById(R.id.cvNewExercise);
        buttonAccept = activity.findViewById(R.id.buttonAccept);
        editExerciseName = activity.findViewById(R.id.editNewExerciseName);


        CardView btnCancel = activity.findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvNewExercise.setVisibility(View.GONE);
            }
        });
    }

    public void initData() {
        tools = db.getAllTools();
        muscles = db.getAllMuscles();
        categories = db.getAllCategories();

        toolsNames = new ArrayList<>();
        musclesNames = new ArrayList<>();
        categoriesNames = new ArrayList<>();

        for (Map.Entry<String, Integer> category : categories.entrySet()) {
            categoriesNames.add(category.getKey());
        }

        for (Map.Entry<String, Integer> tool : tools.entrySet()) {
            toolsNames.add(tool.getKey());
        }

        for (Map.Entry<String, Integer> muscle : muscles.entrySet()) {
            musclesNames.add(muscle.getKey());
        }

        ArrayAdapter<String> spiToolsAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, toolsNames);
        spiToolsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spiTools.setAdapter(spiToolsAdapter);

        ArrayAdapter<String> spiMusclesAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, musclesNames);
        spiMusclesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spiMuscles.setAdapter(spiMusclesAdapter);

        ArrayAdapter<String> spiCategoriesAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, categoriesNames);
        spiCategoriesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spiCategories.setAdapter(spiCategoriesAdapter);
    }

}
