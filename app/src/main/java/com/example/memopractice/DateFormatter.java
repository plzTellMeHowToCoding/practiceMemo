package com.example.memopractice;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat SimpleDateFormat_time = new SimpleDateFormat("HH/mm/ss");
    private static final String TAG = "DateFormatter";

    /*將Date物件的時間轉為HH/mm/ss格式
    * Wed May 22 19:59:25 GMT+08:00 2019 => 19/59/25*/
    public static String dateToTime(Date d){
        String str_time = SimpleDateFormat_time.format(d);
        Log.d(TAG, "@@@@time = "+str_time);
        return str_time;
    }

    /*將Date物件的時間轉為yyyy/MM/dd的格式
    * Wed May 22 19:59:25 GMT+08:00 2019 => 2019/05/22 */
    public static String dateToString(Date d){
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
        //Log.d(TAG, "@@@@strDateToInt: "+Integer.valueOf(sb.toString()));
        return Integer.valueOf(sb.toString());
    }

    public static int strDateToTime(String d){
        String[] str_arr_d = d.split("/");
        StringBuilder sb = new StringBuilder();
        for(String s : str_arr_d){
            sb.append(s);
        }
        Log.d(TAG, "@@@@strDateToInt: "+Integer.valueOf(sb.toString()));
        return Integer.valueOf(sb.toString());
    }

    /*String.subString(start,end) => 從start開始到end-1的位置
     * */
    /*將整數日期轉為字串型態
    * 20190526 => 2019年05月26日*/
    public static String intDateToString(int d){
        StringBuilder sb = new StringBuilder();
        String str_d = String.valueOf(d);
        sb.append(str_d.substring(0,4)).append("年").append(str_d.substring(4,6)).append("月")
                .append(str_d.substring(6,str_d.length())).append("日");
        Log.d(TAG, "@@@@intDateToString: "+sb.toString());
        return sb.toString();
    }
    /*將整數時間轉為字串型態
     * 211228 => 21:12*/
    public static String intTimeToString(int t){
        StringBuilder sb = new StringBuilder();
        String str_t = String.valueOf(t);
        sb.append(str_t.substring(0,2)).append(":").append(str_t.substring(2,4));
        Log.d(TAG, "@@@@intTimeToString: "+sb.toString());
        return sb.toString();
    }

}
