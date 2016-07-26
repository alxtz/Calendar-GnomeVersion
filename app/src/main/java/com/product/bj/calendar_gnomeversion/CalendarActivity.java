package com.product.bj.calendar_gnomeversion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CalendarActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //TODO 設定ViewPager
        setViewPagerPlusAdapter();
    }

    private void setViewPagerPlusAdapter()
    {
        ViewPager calendarViewPager = (ViewPager)findViewById(R.id.CalendarViewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();

        calendarViewPager.setAdapter(new CalendarViewPagerAdapter(fragmentManager));
    }
}

class CalendarViewPagerAdapter extends FragmentPagerAdapter
{
    //TODO 可翻閱的頁數
    int pageMaxCount = 10;

    public CalendarViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment newCalendarPage = new CalendarFragment();

        return newCalendarPage;
    }

    @Override
    public int getCount()
    {
        return pageMaxCount;
    }
}
