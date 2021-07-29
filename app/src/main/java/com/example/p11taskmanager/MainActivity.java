package com.example.p11taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al = dbh.getTasks();
                dbh.close();
                for(Task i: al) {
                    aa.add("1. " + i.getName() + "/n"+ i.getDescription());
                }
                aa.notifyDataSetChanged();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }
}