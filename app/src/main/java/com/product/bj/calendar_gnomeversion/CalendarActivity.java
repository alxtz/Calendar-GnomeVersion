package com.product.bj.calendar_gnomeversion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

        //TODO 設定rootView給adapter調整月年用
        View rootView = this.getWindow().getDecorView();

        CalendarViewPagerAdapter calendarViewPagerAdapter = new CalendarViewPagerAdapter(fragmentManager , calendarViewPager , rootView);

        calendarViewPager.setAdapter(calendarViewPagerAdapter);

        //TODO 設定一開始該focus的頁面
        calendarViewPagerAdapter.setDisplayPage();
    }
}

class CalendarViewPagerAdapter extends FragmentPagerAdapter
{
    //TODO 可翻閱的頁數
    int pageMaxCount;

    public CalendarViewPagerAdapter(FragmentManager fm , ViewPager inputViewPager , View inputRootView)
    {
        super(fm);

        //TODO 用來調整position的ViewPager
        useViewPager = inputViewPager;

        //TODO 用來調整年月用的RootView
        rootView = inputRootView;

        //TODO 設定一開始該有的頁數
        setDefaultPages();

        //TODO 設定一開始的useYear跟useMonth是經過幾天，用來換算星期幾
        setDaysAfter2015_1_1();

    }

    @Override
    public Fragment getItem(int position)
    {
        int giveMonth = 1;
        int giveYear = 2015;

        int monthsAfter2015_1 = position;

        Log.d("FragmentLog","經過的月數為"+Integer.toString(monthsAfter2015_1));

        giveMonth+=monthsAfter2015_1%12;
        giveYear+=monthsAfter2015_1/12;

        Log.d("FragmentLog","頁面為"+Integer.toString(giveYear)+"年"+Integer.toString(giveMonth)+"月");

        int giveThisMonthLength = MONTH_LENGTH_LIST[giveMonth-1];
        int giveLastMonthLength;
        if(giveMonth-2==-1)
        {
            giveLastMonthLength = 31;
        }
        else
        {
            giveLastMonthLength = MONTH_LENGTH_LIST[giveMonth-2];
        }

        Fragment newCalendarPage = new CalendarFragment(giveYear , giveMonth , giveThisMonthLength , giveLastMonthLength);

        return newCalendarPage;
    }

    @Override
    public int getCount()
    {
        setYearMonthWhenScroll();

        if(toAddNewPage==true)
        {
            pageMaxCount++;
            toAddNewPage = false;
            notifyDataSetChanged();
        }

        return pageMaxCount;
    }



    //TODO 實作製作正確的page，跟給予正確的年月的功能
    int defaultYear = 2015;
    int defaultMonth = 1;

    int useYear = 2016;
    int useMonth = 7;

    int defaultPages;
    int defaultPosition;

        //TODO 先設定一開始要創建幾個Page
        private void setDefaultPages()
        {
            int monthsToSet = 0;
            monthsToSet+=(useYear-defaultYear)*12;
            monthsToSet+=useMonth-defaultMonth+1;

            defaultPages = monthsToSet;
            Log.d("CalendarLog","要預先生成的page有"+Integer.toString(defaultPages)+"頁");

            //TODO 把頁面數量設成預設數量+1
            pageMaxCount = defaultPages+1;

            //TODO 設定預設年月的position
            defaultPosition = monthsToSet-1;
        }

        //TODO ViewPager的物件，用來調整顯示的position
        ViewPager useViewPager;
        public void setDisplayPage()
        {
            Log.d("CalendarLog","設定起始頁面為"+Integer.toString(defaultPosition)+"頁");

            useViewPager.setCurrentItem(defaultPosition);

            currentPosition = defaultPosition;
        }

        //TODO 滑動的之後調整年月(偵測)
        int currentPosition;
        private void setYearMonthWhenScroll()
        {
            int newPosition = useViewPager.getCurrentItem();

            if(newPosition>currentPosition)
            {
                //TODO 滑向右邊
                Log.d("CalendarLog","滑向右邊");
                if(useMonth+1==13)
                {
                    useYear++;
                    useMonth=1;
                }
                else
                {
                    useMonth++;
                }
                setMonthYearTextView(useMonth,useYear);

                setIsLeapYear();

                setDaysAfter2015_1_1();

                if(newPosition >= pageMaxCount-2)
                {
                    toAddNewPage = true;
                }
            }
            else if(newPosition<currentPosition)
            {
                //TODO 滑向左邊
                Log.d("CalendarLog","滑向左邊");
                if(useMonth-1==0)
                {
                    useYear--;
                    useMonth=12;
                }
                else
                {
                    useMonth--;
                }
                setMonthYearTextView(useMonth,useYear);

                setIsLeapYear();

                setDaysAfter2015_1_1();
            }
            else
            {
                //TODO 還在原本的頁面
            }

            currentPosition = newPosition;
        }

        //TODO 滑動之後調整年月(實做)
        View rootView;
        private void setMonthYearTextView(int inputMonth , int inputYear)
        {
            TextView monthTextView = (TextView)rootView.findViewById(R.id.TopBarMonth);
            TextView yearTextView = (TextView)rootView.findViewById(R.id.TopBarYear);

            monthTextView.setText(Integer.toString(inputMonth));
            yearTextView.setText(Integer.toString(inputYear));
        }

        //TODO 滑動到底的時候產生新頁
        boolean toAddNewPage = false;

        //年月調整完之後，要設定更詳細的資料
        //TODO 是否為閏年
        boolean isLeapYear;
        private void setIsLeapYear()
        {
            if( (useYear%400==0) || (useYear%4==0 && useYear%100!=0) )
            {
                Log.d("CalendarLog",Integer.toString(useYear)+"是閏年");
                isLeapYear = true;
                MONTH_LENGTH_LIST[1] = 29;
            }
            else
            {
                Log.d("CalendarLog",Integer.toString(useYear)+"是普通年");
                isLeapYear = false;
                MONTH_LENGTH_LIST[1] = 28;
            }
        }

        //TODO 月份天數對照表
        int[] MONTH_LENGTH_LIST = {31 , 0 , 31 , 30 , 31 , 30 , 31 , 31 , 30 , 31 , 30 , 31};

        //TODO 禮拜幾的對照
        static final int DAY_2015_1_1 = 4;

        //TODO 換算經過2015/1/1後幾天，方便換算禮拜幾
        int daysAfter2015_1_1;

        //設定經過2015/1/1後幾天
        private void setDaysAfter2015_1_1()
        {
            daysAfter2015_1_1 = 0;

            for(int i = 2015; i<useYear; ++i)
            {
                if(isLeap(i)==true)
                {
                    daysAfter2015_1_1+=366;
                }
                else
                {
                    daysAfter2015_1_1+=365;
                }
            }

            //Log.d("CalendarLog","2015年1月1日後過了"+Integer.toString(daysAfter2015_1_1)+"天");

            //加完了一年的天數之後，加月的天數
            for(int i = 0; i<useMonth-1; ++i)
            {
                daysAfter2015_1_1+=MONTH_LENGTH_LIST[i];
            }

            if((useMonth-1)==0)
            {
                Log.d( "CalendarLog" , "在"+(useYear-1)+"年"+(12)+"月底時，過了"+daysAfter2015_1_1+"天");
            }
            else
            {
                Log.d("CalendarLog", "在" + useYear + "年" + (useMonth - 1) + "月底時，過了" + daysAfter2015_1_1 + "天");
            }

        }

        //TODO 偵測是否閏年
        private boolean isLeap(int inputYear)
        {
            if( (inputYear%400==0) || (inputYear%4==0 && inputYear%100!=0) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        //TODO 給入一個page起始的禮拜幾，跟天數
        //TODO 要有 1.上個月的天數,這個月的天數,起始是禮拜幾
}
