package com.example.goyal.todo1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {

    Button button;
    EditText editText;
    Intent intent1;
    private String name,date,time,description;
    Calendar myCalendar = Calendar.getInstance();
    ArrayList<Activity> activities= new ArrayList<>();
    EditText ed1,ed2,ed3,ed4;
    long id2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ed1=findViewById(R.id.todo);
        ed2=findViewById(R.id.date);
        ed3=findViewById(R.id.time);
        ed2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Main4Activity.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ed3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Main4Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed3.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        ed4=findViewById(R.id.description);
        intent1 = getIntent();
        id2 = intent1.getLongExtra("id1",-1);
        String[] selectionArg = {id2 + ""};
        String[] columns ={Contract.activity.COLOUMN_NAME,Contract.activity.COLOUMN_ID,Contract.activity.COLOUMN_DATE,Contract.activity.COLOUMN_TIME,Contract.activity.COLOUMN_DESCRIPTION};
        ActivityOpenHelper openHelper = new ActivityOpenHelper(this); // in this we should use 'getApplicationContext()' should be used instead of 'this' for optimization
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query (Contract.activity.TABLE_NAME , columns, Contract.activity.COLOUMN_ID + " = ? ", selectionArg,null,null,Contract.activity.COLOUMN_DATE);
        while(cursor.moveToNext()){
            String activity = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_TIME));
            String description = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_DESCRIPTION));
            long id= cursor.getLong(cursor.getColumnIndex(Contract.activity.COLOUMN_ID));
            ed1.setText(activity);
            ed2.setText(date);
            ed3.setText(time);
            ed4.setText(description);
            Activity activity1 = new Activity(activity,date,time,description);
            activity1.setId(id);
            activities.add(activity1);

        }
        cursor.close();

    }
    public void updateItem (View view){
        name = ed1.getText().toString();
        date = ed2.getText().toString();
        time = ed3.getText().toString();
        description=ed4.getText().toString();

        Intent intent3 = new Intent();
        String[] selectionArgs = {id2 + ""};
        ActivityOpenHelper openHelper = new ActivityOpenHelper(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id",Contract.activity.COLOUMN_ID);
        Activity activity2 = new Activity(name,date,time,description);
        contentValues.put(Contract.activity.COLOUMN_NAME, name);
        contentValues.put(Contract.activity.COLOUMN_DATE, date);
        contentValues.put(Contract.activity.COLOUMN_TIME, time);
        contentValues.put(Contract.activity.COLOUMN_DESCRIPTION, description);
        int checkId =  database.update(Contract.activity.TABLE_NAME,contentValues,Contract.activity.COLOUMN_ID + " = ?",selectionArgs);
        Log.d("checkkk",checkId + " ");
        Toast.makeText(this, checkId + " ", Toast.LENGTH_SHORT).show();
        intent3.putExtra("id4",id2);
        setResult(8,intent3);
        finish();


    }
    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);ed2.setText(sdf.format(myCalendar.getTime()));
    }

}
