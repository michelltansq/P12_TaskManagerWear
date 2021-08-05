package com.example.p12taskmanagerwear;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Button btnAddTask, btnCancel;
    EditText etName, etDescription, etTime;
    int reqCode = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etTime = findViewById(R.id.etTime);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etName.getText().toString().trim().length() != 0 && etDescription.getText().toString().trim().length() != 0 && etTime.getText().toString().trim().length() != 0) {

                    String name = etName.getText().toString();
                    String description = etDescription.getText().toString();
                    int seconds = Integer.parseInt(etTime.getText().toString());


                    DBHelper dbh = new DBHelper(AddActivity.this);

                    boolean exists = false;
                    for(int i = 0; i <  dbh.getTasks().size(); i++){
                        if (name.equals( dbh.getTasks().get(i).getName())){
                            exists = true;
                        }
                    }//end of for loop

                    if (exists == true){
                        Toast.makeText(AddActivity.this, "Task already exists.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dbh.insertTask(description, name);

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, seconds);

                        Intent intent = new Intent(AddActivity.this,
                                TaskReceiver.class);
                        intent.putExtra("reqCode", reqCode);
                        intent.putExtra("name", name);
                        intent.putExtra("description", description);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                AddActivity.this, reqCode,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager am = (AlarmManager)
                                getSystemService(Activity.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                                pendingIntent);

                        Toast.makeText(AddActivity.this, "Task Inserted", Toast.LENGTH_SHORT).show();
                        finish();
                    }//end of task validation

                    dbh.close();
                }//end of text fields validation

                else {
                    Toast.makeText(AddActivity.this, "Please ensure no input is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }//end of onCreate
}//end of class
