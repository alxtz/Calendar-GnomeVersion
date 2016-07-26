package com.product.bj.calendar_gnomeversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startCalendarActivity(View v)
    {
        Intent calendarActivityIntent = new Intent(this,CalendarActivity.class);

        startActivity(calendarActivityIntent);

        finish();
    }
}
