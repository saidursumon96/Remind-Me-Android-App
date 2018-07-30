package jarvis.remindme.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import jarvis.remindme.R;
import jarvis.remindme.alarm.AlarmReceiver;

public class Alarm_Main extends AppCompatActivity {

    TimePicker myTimePicker;
    Calendar calSet , calNow;
    Button buttonstartSetDialog,canclealarm;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm__main);

        AlarmReceiver alarmReceiver = new AlarmReceiver(); //*** Remember my AlarmReceiver class.

        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        canclealarm = (Button)findViewById(R.id.cancleAlarm);

        canclealarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calNow.clear();
                Toast.makeText(getApplicationContext(),"Alarm Cancled.",Toast.LENGTH_SHORT).show();
            }
        });

        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    textAlarmPrompt.setText(""); //*** Set textView null.
                    openTimePickerDialog(false); //*** call this method for time picker dialog.
            }});

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void openTimePickerDialog(boolean is12r){ //*** openTimePickerDialog method for time picker.

        Calendar calendar = Calendar.getInstance(); //*** Use the current time as the default values for the picker
                                                    //*** The Calendar class is an abstract class that provides methods
                                                    //                for converting between a specific instant in time.
        timePickerDialog = new TimePickerDialog(Alarm_Main.this,onTimeSetListener,
                            calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),is12r);
                                                    //*** TimePickerDialog prompts the user for the time of day using a TimePicker.
                                                    //*** get method returns the value of the given calendar field.

        timePickerDialog.setTitle("Set Alarm Time"); //*** Time picker dialog title.
        timePickerDialog.show(); //*** Show the time picker dialog.
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
                        //*** TimePickerDialog.OnTimeSetListener - callback interface used to indicate the user is done filling in the time.
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) { //*** Called onTimeSet method when the user is done
                                                                            //      setting a new time and the dialog has closed.
                                                                            //*** TimePicker is a widget for selecting the time of day.
            calNow = Calendar.getInstance(); //*** getInstance method returns a Calendar object whose calendar fields
                                                      //                  have been initialized with the current date and time.
            calSet = (Calendar) calNow.clone(); //*** clone - creates and returns a copy of this object.

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){  //*** Compares the time values represented by two Calendar objects.
                                                //*** Return 0 if the times of the two Calendar are equal.
                                                //*** Return -1 if the time of this Calendar is before the other one.
                                                //*** Return 1 if the time of this Calendar is after the other one.
                //calSet.add(Calendar.DATE, 1);
                Toast.makeText(getApplicationContext(),"Time Already Passed !",Toast.LENGTH_SHORT).show();
            }
            else if (calSet.equals(calNow)){
                Toast.makeText(getApplicationContext(),"You Can't Set Alarm Current Time !",Toast.LENGTH_SHORT).show();
            }
            else{
                setAlarm(calSet);
                Toast.makeText(getApplicationContext(),"Alarm Set On - " + calSet.getTime(),Toast.LENGTH_SHORT).show();
                                                            //*** getTime method returns a Date object representing this Calendar's time value.
            }
            //setAlarm(calSet);
        }};

    private void setAlarm(Calendar targetCal){ //*** setAlarm method for background service.

        textAlarmPrompt.setText("\n\n\n"+ "AlarmSet  " + targetCal.getTime() + "\n" + "\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0); //*** getBroadcast method retrieve
                                                                                                // a PendingIntent that will perform a broadcast.
                                                                                            //*** Intent: The Intent to be broadcast.
                                                //*** PendingIntent class is a description of an Intent and target action to perform with it.
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);  //*** AlarmManager class provides access
                                                                                            //           to the system alarm services.
                                    //*** getSystemService object return the handle to a system-level service by class.
                                    //*** ALARM_SERVICE - Use with getSystemService(Class) to retrieve a AlarmManager for
                                    //    for informing the user of background events.
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
                                    //*** RTC_WAKEUP - Alarm time in System.currentTimeMillis() which will wake up the device when it goes off.
                                    //*** set - Schedule an alarm.
                                    //*** getTimeInMillis returns this Calendar's current time as a long.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
