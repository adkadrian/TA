package com.example.trainingassistantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnManagePlans;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewElements();

        btnManagePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagePlansActivity.class);
                startActivity(intent);
            }
        });

        database = new DatabaseHelper(this).getWritableDatabase();
        database.close();
    }

    public void initViewElements() {
        btnManagePlans = findViewById(R.id.btnManagePlans);
    }
}