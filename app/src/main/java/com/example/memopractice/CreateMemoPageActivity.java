package com.example.memopractice;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateMemoPageActivity extends AppCompatActivity {

    private TextView create_page_memo_name , create_new_memo_finish;
    private ImageView create_memo_back_key;
    private EditText input_memo;
    private static String str_create_page_memo_name;
    private static final String TAG = "CreateMemoPageActivity";
    private DBHelper mDBHelper = new DBHelper(CreateMemoPageActivity.this,"Memo.db",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memo_page);
        initViews();
    }

    private void saveMemo(){
        SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        String s = DateFormatter.dateToInt(d);
        Log.d(TAG, "@@@@time = "+d.toString());
        //insert date
        contentValues.put("date",DateFormatter.strDateToInt(s));
        //insert content
        contentValues.put("content",input_memo.getText().toString());
        //insert type
        contentValues.put("type","書寫");
        sqLiteDatabase.insert("memo",null,contentValues);
    }

    private void initViews(){
        create_page_memo_name = findViewById(R.id.create_page_memo_name);
        create_page_memo_name.setText(str_create_page_memo_name);

        input_memo = findViewById(R.id.input_memo);

        create_new_memo_finish = findViewById(R.id.create_new_memo_finish);
        create_new_memo_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判斷若user輸入的文字若長度>0則彈出儲存成功提示
                if (input_memo.getText().toString().length() > 0) {
                    saveMemo();
                    Toast.makeText(CreateMemoPageActivity.this, "儲存成功", Toast.LENGTH_LONG).show();
                }
            }
        });

        create_memo_back_key = findViewById(R.id.create_memo_back_key);
        create_memo_back_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_memo.getText().toString().length() > 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateMemoPageActivity.this);
                    builder.setTitle("提示").setMessage("要保存編輯的內容嗎?").setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveMemo();
                            finish();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false).show();
                }else finish();
            }
        });
    }

    public static void startActivity(Context context , String memo_title){
        Intent intent = new Intent(context , CreateMemoPageActivity.class);
        context.startActivity(intent);
        CreateMemoPageActivity.str_create_page_memo_name = memo_title;
    }
}
