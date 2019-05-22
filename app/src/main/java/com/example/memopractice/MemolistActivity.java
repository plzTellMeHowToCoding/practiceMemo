package com.example.memopractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MemolistActivity extends AppCompatActivity {

    private static String memo_title;
    private TextView memo_name;
    private ImageView create_new_memo , back_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memolist);
        initViews();
    }

    private void initViews(){
        memo_name = findViewById(R.id.memo_name);
        //Setting memo_name
        memo_name.setText(memo_title);
        back_key = findViewById(R.id.back_key);
        back_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        create_new_memo = findViewById(R.id.create_new_memo);
        create_new_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Forward to CreateMemoPageActivity with memo name
                CreateMemoPageActivity.startActivity(MemolistActivity.this,memo_name.getText().toString());
            }
        });
    }

    public static void startActivity(Context context , String memo_title){
        Intent intent = new Intent(context , MemolistActivity.class);
        context.startActivity(intent);
        //Getting memo_title from previous activity
        MemolistActivity.memo_title = memo_title;
    }

    class MemoContentRecycler extends RecyclerView.Adapter<MemoContentRecycler.viewHolder>{

        private List list ;

        public MemoContentRecycler(List list){
            this.list = list;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class viewHolder extends RecyclerView.ViewHolder{
            TextView title , date , type;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                title = findViewById(R.id.memo_title);
                date = findViewById(R.id.memo_date);
                type = findViewById(R.id.memo_type);
            }
        }
    }
}
