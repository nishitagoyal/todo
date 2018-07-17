package com.example.goyal.todo1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    Intent intent;
    private String name,date,time,description;
    TextView ed1,ed2,ed3,ed4;
    ArrayList<Activity> activities= new ArrayList<>();
    long id1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        intent = getIntent();
        id1 = intent.getLongExtra("id",-1);
        ed1=findViewById(R.id.todo);
        ed2=findViewById(R.id.date);
        ed3=findViewById(R.id.time);
        ed4=findViewById(R.id.description);
        //int activityid= 0;
        String[] selectionArg = {id1 + ""};
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
//            Activity activity1 = new Activity(activity,date,time,description);
//            activity1.setId(id);
//            activities.add(activity1);

        }
        cursor.close();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main_menu1,menu);
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        if(id==R.id.text)
        {
            Intent intent1 = new Intent (this,Main4Activity.class);
            intent1.putExtra("id1",id1);
            startActivityForResult(intent1,8);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        long id5 = data.getLongExtra("id4",-1);
        String[] selectionArg = {id5 + ""};
        String[] columns ={Contract.activity.COLOUMN_NAME,Contract.activity.COLOUMN_ID,Contract.activity.COLOUMN_DATE,Contract.activity.COLOUMN_TIME,Contract.activity.COLOUMN_DESCRIPTION};
        ActivityOpenHelper openHelper = new ActivityOpenHelper(this); // in this we should use 'getApplicationContext()' should be used instead of 'this' for optimization
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query (Contract.activity.TABLE_NAME , columns, Contract.activity.COLOUMN_ID + " = ? ", selectionArg,null,null,Contract.activity.COLOUMN_DATE);
        while(cursor.moveToNext()){
            String activity = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_TIME));
            String description = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_DESCRIPTION));
            //long id= cursor.getLong(cursor.getColumnIndex(Contract.activity.COLOUMN_ID));
            ed1.setText(activity);
            ed2.setText(date);
            ed3.setText(time);
            ed4.setText(description);
            Activity activity1 = new Activity(activity,date,time,description);
        }
        cursor.close();





    }
}
