package com.example.goyal.todo1;

import android.app.AlarmManager;
import android.support.design.widget.FloatingActionButton;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    ArrayList<Activity> activities= new ArrayList<>();
    ActivityAdapter adapter;
    ListView listView;
    String activity,date,time,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity","OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Main2Activity.class);
                startActivityForResult(intent,0);

            }
        });
        alarmTodoset();
        listView = findViewById(R.id.listview);
        int activityStartingWith = 0;
        String[] selectionArg = {activityStartingWith + ""};
        setdb();
        adapter = new ActivityAdapter (this,activities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }
    public void setdb(){
        String[] columns ={Contract.activity.COLOUMN_NAME,Contract.activity.COLOUMN_ID,Contract.activity.COLOUMN_DATE,Contract.activity.COLOUMN_TIME,Contract.activity.COLOUMN_DESCRIPTION};
        ActivityOpenHelper openHelper = new ActivityOpenHelper(this); // in this we should use 'getApplicationContext()' should be used instead of 'this' for optimization
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query (Contract.activity.TABLE_NAME , columns, null, null,null,null,Contract.activity.COLOUMN_DATE);
        while(cursor.moveToNext()){
            String activity = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_TIME));
            String description = cursor.getString(cursor.getColumnIndex(Contract.activity.COLOUMN_DESCRIPTION));
            long id= cursor.getLong(cursor.getColumnIndex(Contract.activity.COLOUMN_ID));
            Activity activity1 = new Activity(activity,date,time,description);
            activity1.setId(id);
            activities.add(activity1);
        }
        cursor.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.activityid)
        {
            Intent intent = new Intent (this,Main2Activity.class);
            startActivityForResult(intent,0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null) {
            activity = data.getStringExtra(Main2Activity.ACTIVITY_KEY);
            date = data.getStringExtra(Main2Activity.DATE_KEY);
             time = data.getStringExtra(Main2Activity.TIME_KEY);
             description = data.getStringExtra(Main2Activity.DESCRIPTION_KEY);
            // insertInDb();
            Activity activity1 = new Activity(activity,date,time,description);
            //activities.add(activity1);
            ActivityOpenHelper openHelper = new ActivityOpenHelper(this);
            SQLiteDatabase database = openHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //contentValues.put("id",Contract.activity.COLOUMN_ID);
            contentValues.put(Contract.activity.COLOUMN_NAME, activity1.getTodo());
            contentValues.put(Contract.activity.COLOUMN_DATE, activity1.getDate());
            contentValues.put(Contract.activity.COLOUMN_TIME, activity1.getTime());
            contentValues.put(Contract.activity.COLOUMN_DESCRIPTION, activity1.getDescription());
            long id = database.insert(Contract.activity.TABLE_NAME, null, contentValues);//we have equate it to long id because database.insert gives us id of the row which we r inserting
            //Toast.makeText(this,"inserted at " + id, Toast.LENGTH_LONG).show();
            activities.add(activity1);
            activity1.setId(id);
            //Collections.sort(activities);
            adapter.notifyDataSetChanged();
            activities.clear();
            setdb();

        }
        else
        {
            Toast.makeText(this,"Please Enter Details",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Activity activity = activities.get(i);
        final int position=i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete?");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityOpenHelper openHelper = new ActivityOpenHelper(MainActivity.this);
                SQLiteDatabase database = openHelper.getWritableDatabase();
                long id = activity.getId();
                String[] selectionarg ={id + ""};
                database.delete(Contract.activity.TABLE_NAME,Contract.activity.COLOUMN_ID + " = ? ",selectionarg);
                activities.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Activity activity = activities.get(i);
        final int position=i;
        long id=activity.getId();
        Intent intent = new Intent (this,Main3Activity.class);

        intent.putExtra("id",id);
        // Attach the bundled data to the intent
//        intent.putExtra(dataBundle);
        startActivity(intent);
        return false;
    }
    public void alarmTodoset()
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        long CurrentTime = System.currentTimeMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, CurrentTime + 5 * 1000L, pendingIntent);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Log.d("MainActivity","onRecieve");
                // if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle data = intent.getExtras();
                String message = "";

                //Toast.makeText(context, "message", Toast.LENGTH_LONG).show();
                if (data != null) {
                    Object[] pdus = (Object[]) data.get("pdus");
                    String senderNumber = null;
                    long timeStamp = 0;

                    //final SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        senderNumber = sms.getOriginatingAddress();
                        message = sms.getDisplayMessageBody();
                       timeStamp =sms.getTimestampMillis();
                    }
                    //String[] splited = message.split("\\s+");
                    //Toast.makeText(context, "message" + message, Toast.LENGTH_LONG).show();

                    Activity activity1 = new Activity(activity,date,time,description);
                    activity1.setTimeInEpochs(timeStamp);
                    ActivityOpenHelper openHelper = new ActivityOpenHelper(MainActivity.this);
                    SQLiteDatabase database = openHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    //contentValues.put("id",Contract.activity.COLOUMN_ID);
                    contentValues.put(Contract.activity.COLOUMN_NAME, senderNumber);
                    contentValues.put(Contract.activity.COLOUMN_DATE, activity1.getDate());
                    contentValues.put(Contract.activity.COLOUMN_TIME, activity1.getTime());
                    contentValues.put(Contract.activity.COLOUMN_DESCRIPTION, activity1.getDescription());
                    long id = database.insert(Contract.activity.TABLE_NAME, null, contentValues);//we have equate it to long id because database.insert gives us id of the row which we r inserting
                    if(id> -1L)
                    Toast.makeText(MainActivity.this,"inserted at " + id, Toast.LENGTH_LONG).show();

                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, intentFilter);
    }
    public void insertInDb(){
        Activity activity1 = new Activity(activity,date,time,description);
        //activities.add(activity1);
        ActivityOpenHelper openHelper = new ActivityOpenHelper(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id",Contract.activity.COLOUMN_ID);
        contentValues.put(Contract.activity.COLOUMN_NAME, activity1.getTodo());
        contentValues.put(Contract.activity.COLOUMN_DATE, activity1.getDate());
        contentValues.put(Contract.activity.COLOUMN_TIME, activity1.getTime());
        contentValues.put(Contract.activity.COLOUMN_DESCRIPTION, activity1.getDescription());
        long id = database.insert(Contract.activity.TABLE_NAME, null, contentValues);//we have equate it to long id because database.insert gives us id of the row which we r inserting
        //Toast.makeText(this,"inserted at " + id, Toast.LENGTH_LONG).show();
        activities.add(activity1);
        activity1.setId(id);
        //Collections.sort(activities);
        adapter.notifyDataSetChanged();
        activities.clear();
        setdb();
    }
}


