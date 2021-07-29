package com.example.p11taskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    Button btnAddTask, btnCancel;
    EditText etName, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "";
                String description = "";

                if (etName.getText().toString() != "" && etDescription.getText().toString() != "") {
                    name = etName.getText().toString();
                    description = etDescription.getText().toString();
                    DBHelper dbh = new DBHelper(AddActivity.this);
                    dbh.insertTask(description, name);
                }
                else {
                    etName.setError("Please enter your name");
                    etDescription.setError("Please enter your description");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
