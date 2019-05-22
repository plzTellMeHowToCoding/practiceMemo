package com.example.memopractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        memo = findViewById(R.id.memo);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Forward to MemolistActivity with memo name
                MemolistActivity.startActivity(MainActivity.this,memo.getText().toString());
            }
        });
    }
}
