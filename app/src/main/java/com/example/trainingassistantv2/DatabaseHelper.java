package com.example.trainingassistantv2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String EXERCISES_TABLE = "EXERCISES";
    private static final String EXERCISE_ID = "ID";
    private static final String EXERCISE_NAME = "NAME";

    private static final String EXERCISE_CATEGORY_ID = "CATEGORY_ID";
    private static final String EXERCISE_MUSCLE_ID = "MUSCLE_ID";
    private static final String EXERCISE_TOOL_ID = "TOOL_ID";

    private static final String TOOLS_TABLE = "TOOLS";
    private static final String TOOL_ID = "ID";
    private static final String TOOL_NAME = "NAME";

    private static final String MUSCLES_TABLE = "MUSCLES";
    private static final String MUSCLE_ID = "ID";
    private static final String MUSCLE_NAME = "NAME";

    private static final String CATEGORIES_TABLE = "CATEGORIES";
    private static final String CATEGORY_ID = "ID";
    private static final String CATEGORY_NAME = "NAME";

    private final Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "assistant", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createToolsTable = "CREATE TABLE " + TOOLS_TABLE + "(" +
                TOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TOOL_NAME + " TEXT)";
        db.execSQL(createToolsTable);

        ArrayList<String> tools_names = new ArrayList<>();
        tools_names.add("barbell");
        tools_names.add("dumbbell");
        tools_names.add("kettlebell");
        tools_names.add("body weight");
        tools_names.add("rubber");
        tools_names.add("lift");
        tools_names.add("none");

        for (String tool_name: tools_names) {
            String insertIntoTools = "INSERT INTO " + TOOLS_TABLE + "(" + TOOL_NAME + ") VALUES ('" + tool_name + "')";
            db.execSQL(insertIntoTools);
        }

        String createMusclesTable = "CREATE TABLE " + MUSCLES_TABLE + "(" + MUSCLE_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " + MUSCLE_NAME + " TEXT)";
        db.execSQL(createMusclesTable);

        ArrayList<String> muscle_names = new ArrayList<>();
        muscle_names.add("klatka piersiowa");
        muscle_names.add("barki");
        muscle_names.add("brzuch");
        muscle_names.add("biceps");
        muscle_names.add("triceps");
        muscle_names.add("plecy");
        muscle_names.add("czworogłowe nóg");
        muscle_names.add("dwugłowe nóg");
        muscle_names.add("łydki");

        for (String muscle: muscle_names) {
            String insertIntoMuscles = "INSERT INTO " + MUSCLES_TABLE + "(" + MUSCLE_NAME + ") VALUES ('" + muscle + "')";
            db.execSQL(insertIntoMuscles);
        }

        String createCategoriesTable = "CREATE TABLE " + CATEGORIES_TABLE + "(" + CATEGORY_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " + CATEGORY_NAME + " TEXT)";
        db.execSQL(createCategoriesTable);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("A");
        categories.add("B");
        categories.add("C");

        for (String category: categories) {
            String insertIntoCategories = "INSERT INTO " + CATEGORIES_TABLE + "(" + CATEGORY_NAME + ") VALUES ('" + category + "')";
            db.execSQL(insertIntoCategories);
        }

        String createExercisesTable = "CREATE TABLE " + EXERCISES_TABLE + "(" +
                EXERCISE_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                EXERCISE_NAME + " TEXT, " +
                EXERCISE_CATEGORY_ID + " INTEGER, " +
                EXERCISE_MUSCLE_ID + " INTEGER, " +
                EXERCISE_TOOL_ID + " INTEGER," +
                "FOREIGN KEY (" + EXERCISE_MUSCLE_ID +") REFERENCES " + MUSCLES_TABLE + " (" + MUSCLE_ID + "), " +
                "FOREIGN KEY (" + EXERCISE_CATEGORY_ID +") REFERENCES " + CATEGORIES_TABLE + " (" + CATEGORY_ID + "), " +
                "FOREIGN KEY (" + EXERCISE_TOOL_ID +") REFERENCES " + TOOLS_TABLE + " (" + TOOL_ID + "))";
        db.execSQL(createExercisesTable);

        ArrayList<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise("wyciskanie sztangi na ławce płaskiej", "barbell", "klatka piersiowa", "A"));
        exercises.add(new Exercise("wyciskanie sztangi na skosie dodatnim", "barbell", "klatka piersiowa", "A"));
        exercises.add(new Exercise("wyciskanie hantli na ławce płaskiej", "dumbbell", "klatka piersiowa", "A"));
        exercises.add(new Exercise("wyciskanie hantli na ławce skośnej", "dumbbell", "klatka piersiowa", "A"));

        exercises.add(new Exercise("krzyżowanie linek wyciągu górnego z góry do dołu", "lift", "klatka piersiowa", "B"));
        exercises.add(new Exercise("krzyżowanie linek wyciągu dolnego z dołu do góry", "lift", "klatka piersiowa", "B"));
        exercises.add(new Exercise("rozpiętki na ławce płaskiej", "dumbbell", "klatka piersiowa", "B"));
        exercises.add(new Exercise("rozpiętki na ławce skośnej", "dumbbell", "klatka piersiowa", "B"));
        exercises.add(new Exercise("rozpiętki w leżeniu na podłodze", "dumbbell", "klatka piersiowa", "B"));
        exercises.add(new Exercise("bayesian flyes", "dumbbell", "klatka piersiowa", "B"));

        exercises.add(new Exercise("wyciskanie sztangi nad głowę stojąc", "barbell", "barki", "A"));
        exercises.add(new Exercise("wyciaskanie sztangi nad głowę siedząc", "barbell", "barki", "A"));
        exercises.add(new Exercise("wyciskanie hantli nad głowę stojąc", "dumbbell", "barki", "A"));
        exercises.add(new Exercise("wyciskanie hantli nad głowę siedząc", "dumbbell", "barki", "A"));

        exercises.add(new Exercise("unoszenie hantli bokiem stojąc", "dumbbell", "barki", "B"));
        exercises.add(new Exercise("unoszenie hantli bokiem w oparciu o ławkę stojąc", "dumbbell", "barki", "B"));
        exercises.add(new Exercise("unoszenie ramoin w bok na wyciągu", "lift", "barki", "B"));
        exercises.add(new Exercise("przyciąganie sznurków do brody na wyciągu", "lift", "barki", "B"));

        exercises.add(new Exercise("facepull", "lift", "barki", "C"));
        exercises.add(new Exercise("barbell facepull", "barbell", "barki", "C"));
        exercises.add(new Exercise("unoszenie hantli w opadzie tułowia", "dumbbell", "barki", "C"));
        exercises.add(new Exercise("odwrotne rozpiętki na ławce skośnej", "dumbbell", "barki", "C"));

        exercises.add(new Exercise("facepull", "lift", "barki", "C"));
        exercises.add(new Exercise("barbell facepull", "barbell", "barki", "C"));
        exercises.add(new Exercise("unoszenie hantli w opadzie tułowia", "dumbbell", "barki", "C"));
        exercises.add(new Exercise("odwrotne rozpiętki na ławce skośnej", "dumbbell", "barki", "C"));

        exercises.add(new Exercise("uginanie hantli francuskie stojąc", "dumbbell", "triceps", "A"));
        exercises.add(new Exercise("uginanie hantli francuskie siedząc", "dumbbell", "triceps", "A"));
        exercises.add(new Exercise("Uginanie hantli francusskie leżąc", "dumbbell", "triceps", "A"));
        exercises.add(new Exercise("Uginanie sztangi francusskie leżąc", "barbell", "triceps", "A"));
        exercises.add(new Exercise("Prostowanie ramion na wyciągu", "lift", "triceps", "A"));

        exercises.add(new Exercise("Dipy na poręczach", "none", "triceps", "B"));
        exercises.add(new Exercise("Wyciskanie sztangi wąsko", "barbell", "triceps", "B"));

        for (Exercise exercise: exercises) {
            String name = exercise.getName();
            String muscle = exercise.getMuscle();
            String category = exercise.getCategory();
            String tool = exercise.getTool();

            String insertIntoExercises = "INSERT INTO " + EXERCISES_TABLE + "(" + EXERCISE_NAME + ", " + EXERCISE_MUSCLE_ID + ", " + EXERCISE_CATEGORY_ID + ", " + EXERCISE_TOOL_ID + ") VALUES (" +
                    "'" + name + "'," +
                    "(SELECT " + MUSCLE_ID + " FROM " + MUSCLES_TABLE + " WHERE NAME = '" + muscle + "')," +
                    "(SELECT " + CATEGORY_ID + " FROM " + CATEGORIES_TABLE + " WHERE NAME = '" + category + "')," +
                    "(SELECT " + TOOL_ID + " FROM " + TOOLS_TABLE + " WHERE NAME = '" + tool + "'))";

            db.execSQL(insertIntoExercises);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectAllExercises = "SELECT E." + EXERCISE_ID + ", E." + EXERCISE_NAME + ", T." + TOOL_NAME + ", M." + MUSCLE_NAME + ", C." + CATEGORY_ID + " " +
                "FROM " + EXERCISES_TABLE + " E " +
                "JOIN " + TOOLS_TABLE + " T " +
                "ON E." + EXERCISE_TOOL_ID + " = T." + TOOL_ID + " " +
                "JOIN " + MUSCLES_TABLE + " M " +
                "ON E." + EXERCISE_MUSCLE_ID + " = M." + MUSCLE_ID + " " +
                "JOIN " + CATEGORIES_TABLE + " C " +
                "ON E." + EXERCISE_CATEGORY_ID + " = C." + CATEGORY_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllExercises, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String tool = cursor.getString(2);
                String muscle = cursor.getString(3);
                String category = cursor.getString(4);

                Exercise exercise = new Exercise(id, name, tool, muscle, category);
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return exercises;
    }

    public ArrayList<Exercise> getAllExercisesByMuscle(int muscleGroup) {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectExercisesByMuscle = "SELECT E." + EXERCISE_ID + ", E." + EXERCISE_NAME + ", T." + TOOL_NAME + ", M." + MUSCLE_NAME + ", C." + CATEGORY_NAME + " " +
                "FROM " + EXERCISES_TABLE + " E " +
                "JOIN " + TOOLS_TABLE + " T " +
                "ON E." + EXERCISE_TOOL_ID + " = T." + TOOL_ID + " " +
                "JOIN " + MUSCLES_TABLE + " M " +
                "ON E." + EXERCISE_MUSCLE_ID + " = M." + MUSCLE_ID + " " +
                "JOIN " + CATEGORIES_TABLE + " C " +
                "ON E." + EXERCISE_CATEGORY_ID + " = C." + CATEGORY_ID + " " +
                "WHERE " + EXERCISE_MUSCLE_ID + " = " + muscleGroup;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectExercisesByMuscle, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String tool = cursor.getString(2);
                String muscle = cursor.getString(3);
                String category = cursor.getString(4);

                Exercise exercise = new Exercise(id, name, tool, muscle, category);
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return  exercises;
    }

    public ArrayList<Group> getAllMuscleGroups() {
        ArrayList<Group> groups = new ArrayList<>();

        String selectAllMuscleGroups = "SELECT " + MUSCLE_ID + ", " + MUSCLE_NAME + " FROM " + MUSCLES_TABLE + " ORDER BY " + MUSCLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllMuscleGroups, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                groups.add(new Group(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return groups;
    }

    public void deleteExerciseByID(int id) {
        String exerciseId = String.valueOf(id);
        String deleteExerciseById = "DELETE FROM " + EXERCISES_TABLE + " WHERE " + EXERCISE_ID + " = " + exerciseId;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(deleteExerciseById);
            Toast.makeText(context, "Exercise deleted", Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            Toast.makeText(context, "Something gone wrong", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public Map<String, Integer> getAllTools() {
        Map<String, Integer> tools = new HashMap<>();

        String selectAllTools = "SELECT " + TOOL_ID + ", " + TOOL_NAME + " FROM " + TOOLS_TABLE + " ORDER BY " + TOOL_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllTools, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                tools.put(name, id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tools;
    }

    public Map<String, Integer> getAllMuscles() {
        Map<String, Integer> muscles = new HashMap<>();

        String selectAllMuscles = "SELECT " + MUSCLE_ID + ", " + MUSCLE_NAME + " FROM " + MUSCLES_TABLE + " ORDER BY " + MUSCLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllMuscles, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                muscles.put(name, id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return muscles;
    }

    public Map<String, Integer> getAllCategories() {
        Map<String, Integer> categories = new HashMap<>();

        String selectAllCategories = "SELECT " + CATEGORY_ID + ", " + CATEGORY_NAME + " FROM " + CATEGORIES_TABLE + " ORDER BY " + CATEGORY_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllCategories, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                categories.put(name, id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void updateExerciseByID(int id, String name, int toolId, int muscleId, int categoryId) {
        String exerciseId = String.valueOf(id);
        String exerciseTool = String.valueOf(toolId);
        String exerciseMuscle = String.valueOf(muscleId);
        String exerciseCategory = String.valueOf(categoryId);

        String updateExerciseById = "UPDATE " + EXERCISES_TABLE + " " +
                "SET " + EXERCISE_NAME + " = '" + name + "', " +
                EXERCISE_TOOL_ID + " = " + exerciseTool + ", " +
                EXERCISE_MUSCLE_ID + " = " + exerciseMuscle + ", " +
                EXERCISE_CATEGORY_ID + " = " + exerciseCategory +
                " WHERE " + EXERCISE_ID + " = " + exerciseId;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(updateExerciseById);
            Toast.makeText(context, "Exercise updated", Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            Toast.makeText(context, "Something gone wrong", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void insertNewExercise(Exercise newExercise) {
        String name = newExercise.getName();
        String tool = newExercise.getTool();
        String muscle = newExercise.getMuscle();
        String category = newExercise.getCategory();

        String insertNewExercise = "INSERT INTO " + EXERCISES_TABLE + "(" + EXERCISE_NAME + ", " + EXERCISE_MUSCLE_ID + ", " + EXERCISE_CATEGORY_ID + ", " + EXERCISE_TOOL_ID + ") VALUES (" +
                "'" + name + "'," +
                "(SELECT " + MUSCLE_ID + " FROM " + MUSCLES_TABLE + " WHERE NAME = '" + muscle + "')," +
                "(SELECT " + CATEGORY_ID + " FROM " + CATEGORIES_TABLE + " WHERE NAME = '" + category + "')," +
                "(SELECT " + TOOL_ID + " FROM " + TOOLS_TABLE + " WHERE NAME = '" + tool + "'))";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(insertNewExercise);
            Toast.makeText(context, "Exercise added", Toast.LENGTH_LONG).show();
            db.close();
        } catch (Exception exception) {
            Toast.makeText(context, "Something gone wrong", Toast.LENGTH_LONG).show();
        }
    }

    public String getMuscleNameByID(int id) {
        String muscleId = String.valueOf(id);
        String muscleName = null;

        String selectMuscleNameById = "SELECT " + MUSCLE_NAME + " " +
                "FROM " + MUSCLES_TABLE + " " +
                "WHERE " + MUSCLE_ID + " = " + muscleId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectMuscleNameById, null);

        if (cursor.moveToFirst()) {
            muscleName = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return muscleName;
    }
}
