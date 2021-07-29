package com.example.p11taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Task> al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);
        al = new ArrayList<Task>();
        lv = findViewById(R.id.lv);
        aa = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al = dbh.getTasks();
        dbh.close();
        aa.notifyDataSetChanged();

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al = dbh.getTasks();
        dbh.close();
        aa.notifyDataSetChanged();
    }
}