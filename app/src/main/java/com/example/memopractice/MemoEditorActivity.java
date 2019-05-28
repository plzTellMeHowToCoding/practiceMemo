package com.example.memopractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MemoEditorActivity extends AppCompatActivity {

    private ImageView back_kay;
    private TextView memo_name,last_edit_time;
    private EditText input_area;
    private static final String TAG = "MemoEditorActivity";
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);
        bundle = getIntent().getBundleExtra("data");
        Log.d(TAG, "@@@@onCreate: time = "+bundle.getInt("memo_time"));
        Log.d(TAG, "@@@@onCreate: date = "+bundle.getInt("memo_date"));
        Log.d(TAG, "@@@@onCreate: date = "+bundle.getString("memo_content"));
        Log.d(TAG, "@@@@onCreate: date = "+bundle.getString("memo_name"));
        initViews();
    }

    private void initViews(){
        back_kay = findViewById(R.id.edit_memo_back_key);
        back_kay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        input_area = findViewById(R.id.edit_input_memo);
        input_area.setText(bundle.getString("memo_content"));
        memo_name = findViewById(R.id.edit_memo_name);
        memo_name.setText(bundle.getString("memo_name"));
        last_edit_time = findViewById(R.id.last_edit_time);
        //String str_d = String.valueOf(bundle.getInt("memo_date"));
        //StringBuilder sb = new StringBuilder();
        //sb.append(str_d.substring(0,4)).append("年").append(str_d.substring(4,6)).append("月").append(str_d.substring(6,str_d.length())).append("日");
        //Log.d(TAG, "@@@@initViews: sb = "+sb.toString());
        last_edit_time.setText(DateFormatter.intDateToString(bundle.getInt("memo_date"))+" "
                +DateFormatter.intTimeToString(bundle.getInt("memo_time")));
    }

    public static void startActivity(Context context , Bundle bundle){
        Intent intent = new Intent(context,MemoEditorActivity.class);
        intent.putExtra("data",bundle);
        context.startActivity(intent);
    }
}
