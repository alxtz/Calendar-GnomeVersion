package com.product.bj.calendar_gnomeversion;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View returnCalendarFragment = inflater.inflate(R.layout.fragment_calendar , container , false);

        return returnCalendarFragment;
    }

    public CalendarFragment(int inputYear , int inputMonth , int inputThisMonthLength , int inputLastMonthLength)
    {
        useMonth = inputMonth;
        useYear = inputYear;

        thisMonthLenth = inputThisMonthLength;
        lastMonthLenth = inputLastMonthLength;

        Log.d("FragmentLog","我的年月為"+Integer.toString(useYear)+"年"+Integer.toString(useMonth)+"月!");
        Log.d("FragmentLog","我的本月天數為"+Integer.toString(thisMonthLenth));
        Log.d("FragmentLog","我的上個月天數為"+Integer.toString(lastMonthLenth));
    }

    int useYear;
    int useMonth;

    int thisMonthLenth;
    int lastMonthLenth;

    //TODO 要根據上個月,這個月的天數,以及1號是禮拜幾來排月曆

}
