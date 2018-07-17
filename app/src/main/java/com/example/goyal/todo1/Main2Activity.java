package com.example.goyal.todo1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {
    public static final String ACTIVITY_KEY= "activity";
    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String DESCRIPTION_KEY = "description";
    Intent intent;
   // TextView textView;
    Calendar myCalendar = Calendar.getInstance();
    EditText editText,editText1,editText2,editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         intent = getIntent();
        editText = findViewById(R.id.edittext);
        editText3=findViewById(R.id.description);
        editText1=findViewById(R.id.date);
         editText2=findViewById(R.id.time);
         editText1.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View v) {
                                             // TODO Auto-generated method stub
                                             new DatePickerDialog(Main2Activity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                     myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                         }
                                     });
        editText2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        Intent intentfromotherapp = getIntent();
        String action = intentfromotherapp.getAction();
        String type = intentfromotherapp.getType();
        if(Intent.ACTION_SEND.equals(action) && type!=null){
            if("text/plain".equals(type)){
                String sharedtext = intentfromotherapp.getStringExtra(Intent.EXTRA_TEXT);
                if(sharedtext!=null)
                    editText.setText(sharedtext);
            }
        }

    }
    public void saveActivity (View view)
    {

        String activity = editText.getText().toString();
        String date = editText1.getText().toString();
        String time = editText2.getText().toString();
        String description = editText3.getText().toString();
        Intent data = new Intent();
        data.putExtra(ACTIVITY_KEY,activity);
        data.putExtra(DATE_KEY,date);
        data.putExtra(TIME_KEY,time);
        data.putExtra(DESCRIPTION_KEY,description);
        setResult(1,data);
        finish();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);editText1.setText(sdf.format(myCalendar.getTime()));
    }




}
