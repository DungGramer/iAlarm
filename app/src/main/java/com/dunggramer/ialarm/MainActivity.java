package com.dunggramer.ialarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    int mHour, mMin;
    TextView textView;
    View stopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.clock);
        textView = findViewById(R.id.textView);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMin = minute;
            }
        });
    }

    public void setTimer(View v) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        String min = (mMin < 10) ?  min = "0" + mMin : String.valueOf(mMin);

        textView.setText("You are set alarm in " + mHour + ":" + min);

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
        cal_alarm.set(Calendar.MINUTE, mMin);
        cal_alarm.set(Calendar.SECOND, 0);

        if(cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }
        Intent i  = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        Context context;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 24444, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }

}