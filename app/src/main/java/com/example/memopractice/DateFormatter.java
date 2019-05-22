package com.example.memopractice;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class DateFormatter {

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd"); ;


    /*將Date物件的時間轉為yyyy/MM/dd的格式
    * Wed May 22 19:59:25 GMT+08:00 2019 => 2019/05/22 */
    public static String dateToInt(Date d){
        String str_date = mSimpleDateFormat.format(d);
        Log.d(TAG, "@@@@date = "+str_date);
        return str_date;
    }
    /*將字串日期轉換為整數型態
    * 2019/05/22 => 20190522*/
    public static int strDateToInt(String d){
        String[] str_arr_d = d.split("/");
        StringBuilder sb = new StringBuilder();
        for(String s : str_arr_d){
            sb.append(s);
        }
        Log.d(TAG, "@@@@strDateToInt: "+Integer.valueOf(sb.toString()));
        return Integer.valueOf(sb.toString());
    }
}
