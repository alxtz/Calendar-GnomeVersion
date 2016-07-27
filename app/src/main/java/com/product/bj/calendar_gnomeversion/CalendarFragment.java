package com.product.bj.calendar_gnomeversion;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View returnCalendarFragment = inflater.inflate(R.layout.fragment_calendar , container , false);

        useFragmentView = returnCalendarFragment;

        setDayBlockDate();

        //TODO 設定可以focus
        setDayBlockClickable();

        return returnCalendarFragment;
    }

    public CalendarFragment(int inputYear , int inputMonth , int inputThisMonthLength , int inputLastMonthLength , int inputWhatDay)
    {
        useMonth = inputMonth;
        useYear = inputYear;

        thisMonthLenth = inputThisMonthLength;
        lastMonthLenth = inputLastMonthLength;

        whatDay = inputWhatDay;

        Log.d("FragmentLog","--------------------");
        Log.d("FragmentLog","我的年月為"+Integer.toString(useYear)+"年"+Integer.toString(useMonth)+"月!");
        Log.d("FragmentLog","我的本月天數為"+Integer.toString(thisMonthLenth));
        Log.d("FragmentLog","我的上個月天數為"+Integer.toString(lastMonthLenth));
        Log.d("FragmentLog","我的這個月初為禮拜"+Integer.toString(whatDay));
        Log.d("FragmentLog","--------------------");
    }

    int useYear;
    int useMonth;

    int thisMonthLenth;
    int lastMonthLenth;

    //本月初是幾號
    int whatDay;

    //TODO 要根據上個月,這個月的天數,以及1號是禮拜幾來排月曆
    //使用的findViewByID所使用的View
    View useFragmentView;

    private void setDayBlockDate()
    {
        //設定這個月的日期
        int startDateBlockNum = whatDay+1;

        int dateCount = 1;

        for(int i=startDateBlockNum; i<startDateBlockNum+thisMonthLenth; ++i)
        {
            String IDName = "DateText"+i;
            int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            TextView dateTextView = (TextView)useFragmentView.findViewById(resID);
            dateTextView.setText(Integer.toString(dateCount));
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX , 15);
            dateTextView.setTypeface(null, Typeface.BOLD);
            dateCount++;

            IDName = "DayBlock"+i;
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            RelativeLayout dayBlock = (RelativeLayout)useFragmentView.findViewById(resID);
            dayBlock.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        dateCount = 1;

        //設定下個月的日期
        for(int i=startDateBlockNum+thisMonthLenth; i<=42; ++i)
        {
            String IDName = "DateText"+i;
            int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            TextView dateTextView = (TextView)useFragmentView.findViewById(resID);
            dateTextView.setText(Integer.toString(dateCount));
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX , 12);
            dateTextView.setTypeface(null, Typeface.ITALIC);
            dateCount++;

            IDName = "DayBlock"+i;
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            RelativeLayout dayBlock = (RelativeLayout)useFragmentView.findViewById(resID);
            dayBlock.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

        dateCount = lastMonthLenth;
        //設定上個月的日期
        for(int i=startDateBlockNum-1; i>0; --i)
        {
            String IDName = "DateText"+i;
            int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            TextView dateTextView = (TextView)useFragmentView.findViewById(resID);
            dateTextView.setText(Integer.toString(dateCount));
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX , 12);
            dateTextView.setTypeface(null, Typeface.ITALIC);
            dateCount--;

            IDName = "DayBlock"+i;
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            RelativeLayout dayBlock = (RelativeLayout)useFragmentView.findViewById(resID);
            dayBlock.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }
    }

    //TODO 設定當某個dayBlock被按到時，展示紅框

    private void setDayBlockClickable()
    {
        /*
        RelativeLayout dayblock = (RelativeLayout)useFragmentView.findViewById(R.id.DayBlock1);

        dayblock.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("ClickLog","Clicked!");
                    RelativeLayout border1 = (RelativeLayout)useFragmentView.findViewById(R.id.DayBlock1Border1);
                    border1.setVisibility(View.VISIBLE);
                    RelativeLayout border2 = (RelativeLayout)useFragmentView.findViewById(R.id.DayBlock1Border2);
                    border2.setVisibility(View.VISIBLE);
                    RelativeLayout border3 = (RelativeLayout)useFragmentView.findViewById(R.id.DayBlock1Border3);
                    border3.setVisibility(View.VISIBLE);
                    RelativeLayout border4 = (RelativeLayout)useFragmentView.findViewById(R.id.DayBlock1Border4);
                    border4.setVisibility(View.VISIBLE);
                }
            }
        );
        */

        for(int i=1; i<=42; ++i)
        {
            String IDName = "DayBlock"+i;
            int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            RelativeLayout dayblock = (RelativeLayout)useFragmentView.findViewById(resID);
            final int giveInt = i;
            dayblock.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Log.d("FragmentLog","DayBlock"+giveInt+" Clicked!");
                        IntToPass = giveInt;
                        setFocusBlock(giveInt);
                    }
                }
            );
        }
    }

    int IntToPass;
    int currentFocusBlock = -1;

    private void setFocusBlock(int inputBlockNum)
    {
        //TODO 如果現在focus的跟點擊的一樣的話，就把邊框取消，currentFocus改成-1
        if(currentFocusBlock==inputBlockNum)
        {
            String IDName = "DayBlock"+inputBlockNum+"Border1";
            int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            RelativeLayout border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.INVISIBLE);
            IDName = "DayBlock"+inputBlockNum+"Border2";
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.INVISIBLE);
            IDName = "DayBlock"+inputBlockNum+"Border3";
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.INVISIBLE);
            IDName = "DayBlock"+inputBlockNum+"Border4";
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.INVISIBLE);
            currentFocusBlock=-1;
        }
        else
        {
            if(currentFocusBlock!=-1)
            {
                String IDName = "DayBlock"+currentFocusBlock+"Border1";
                int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
                RelativeLayout border = (RelativeLayout)useFragmentView.findViewById(resID);
                border.setVisibility(View.INVISIBLE);
                IDName = "DayBlock"+currentFocusBlock+"Border2";
                resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
                border = (RelativeLayout)useFragmentView.findViewById(resID);
                border.setVisibility(View.INVISIBLE);
                IDName = "DayBlock"+currentFocusBlock+"Border3";
                resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
                border = (RelativeLayout)useFragmentView.findViewById(resID);
                border.setVisibility(View.INVISIBLE);
                IDName = "DayBlock"+currentFocusBlock+"Border4";
                resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
                border = (RelativeLayout)useFragmentView.findViewById(resID);
                border.setVisibility(View.INVISIBLE);
            }

            String IDName = "DayBlock"+inputBlockNum+"Border1";
            int resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            RelativeLayout border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.VISIBLE);
            IDName = "DayBlock"+inputBlockNum+"Border2";
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.VISIBLE);
            IDName = "DayBlock"+inputBlockNum+"Border3";
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.VISIBLE);
            IDName = "DayBlock"+inputBlockNum+"Border4";
            resID = useFragmentView.getResources().getIdentifier(IDName,"id",getActivity().getPackageName());
            border = (RelativeLayout)useFragmentView.findViewById(resID);
            border.setVisibility(View.VISIBLE);

            currentFocusBlock=inputBlockNum;
        }
    }

}
