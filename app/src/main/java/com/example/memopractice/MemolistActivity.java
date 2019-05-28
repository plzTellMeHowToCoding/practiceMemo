package com.example.memopractice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MemolistActivity extends AppCompatActivity{

    private static String memo_title;
    private TextView memo_name , bottom_hint , edit;
    private ImageView create_new_memo , back_key;
    private DBHelper mDBHelper;
    private static List<Memo> list;
    private RecyclerView mRecyclerView;
    private MemoContentRecycler mMemoContentRecycler;
    private static final String TAG = "MemolistActivity";

    public static void addDataToList(Memo memo){
        list.add(memo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memolist);
        initViews();
        list = initRecyclerViews();
        //判斷list是否有資料，若有資料則將底下的hint改為x則備忘錄
        if(list.size() > 0) {
            bottom_hint.setText(list.size() + "則備忘錄");
        }
        Log.d(TAG, "@@@@onCreate: list len = "+list.size());
        mMemoContentRecycler = new MemoContentRecycler(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MemolistActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setAdapter(mMemoContentRecycler);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "@@@@onRestart: list len = "+list.size());
        //為了將最後插入的data顯示在第一筆，顧notifyItemInserted設置為0
        //調用notifyItemInserted後，Recycler view adapter會自動呼叫onBindViewHolder，並將該方法的參數i設置為最後插入list的index
        mMemoContentRecycler.notifyItemInserted(0);
        mRecyclerView.scrollToPosition(0);
    }

    private boolean isShow = false;
    private void initViews(){
        mRecyclerView = findViewById(R.id.memo_recycler_view);
        memo_name = findViewById(R.id.memo_name);
        //Setting memo_name
        memo_name.setText(memo_title);
        bottom_hint = findViewById(R.id.bottom_hint);

        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每次click時將isShow做反向
                isShow = !isShow;
                //呼叫setCheckBoxVisible()方法，將isShow傳入
                mMemoContentRecycler.setCheckBoxVisible(isShow);
            }
        });

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

    private List<Memo> initRecyclerViews(){
        List<Memo> memo_list = new ArrayList<>();
        mDBHelper = new DBHelper(this,"Memo.db",null,1);
        SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("memo",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                Memo memo = new Memo(cursor.getInt(cursor.getColumnIndex("date")),
                        cursor.getInt(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("content")));
                memo_list.add(memo);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "@@@@ list len = "+memo_list.size());
        return memo_list;
    }

    public static void startActivity(Context context , String memo_title){
        Intent intent = new Intent(context , MemolistActivity.class);
        context.startActivity(intent);
        //Getting memo_title from previous activity
        MemolistActivity.memo_title = memo_title;
    }

    class MemoContentRecycler extends RecyclerView.Adapter<MemoContentRecycler.viewHolder>{

        private List<Memo> list ;
        public MemoContentRecycler(List<Memo> list){
            this.list = list;
        }
        private boolean isShow = false;
        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo_recycler_content,viewGroup,false);
            final viewHolder holder = new viewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    /*將左上角memo_title,備忘錄內容,編輯日期傳給MemoEditorActivity*/
                    bundle.putString("memo_name",memo_title);
                    /*要使用list.size() - holder.AdapterPosition -1的原因是
                    * holder.AdapterPosition會返回點選view位於Adapter中哪個位置，由於有將最新貼文列在最上方
                    * 故最上面的元件會是0，而直接調用list.get(0)會得到最舊的物件*/
                    bundle.putString("memo_content",list.get(list.size()-holder.getAdapterPosition()-1).getContent());
                    bundle.putInt("memo_date",list.get(list.size()-holder.getAdapterPosition()-1).getDate());
                    bundle.putInt("memo_time",list.get(list.size()-holder.getAdapterPosition()-1).getTime());
                    Log.d(TAG, "@@@@onClick: "+list.get(list.size()-holder.getAdapterPosition()-1).getTime());
                    MemoEditorActivity.startActivity(viewGroup.getContext(),bundle);
                }
            });
            return holder;
        }

        //對isShow()進行賦值
        public void setCheckBoxVisible(boolean isShow){
            this.isShow = isShow;
            Log.d(TAG, "@@@@setCheckBoxVisible: is Show = "+isShow);
            //更改isShow()後，由於isShow的值改變，因此使用notifyDataSetChanged()提醒Recycler view Adapter有變數值更改
            //執行此方法後recycler view中的 sub view會更新顯示
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
            //讓新增的memo能夠先取出顯示
            Memo memo = list.get(list.size()-1-i);
            //Memo memo = list.get(i);
            Log.d(TAG, "@@@@onBindViewHolder: ");
            //若isShow == false則隱藏子view中的check box
            if(isShow == false)
                viewHolder.CheckBox.setVisibility(View.GONE);
            //若isShow == true則顯示子view中的check box
            else
                viewHolder.CheckBox.setVisibility(View.VISIBLE);

            viewHolder.date.setText(String.valueOf(memo.getDate()));
            viewHolder.type.setText(memo.getType());
            viewHolder.title.setText(memo.getContent());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class viewHolder extends RecyclerView.ViewHolder{
            TextView title , date , type;
            CheckBox CheckBox;
            View view;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.memo_title);
                date = itemView.findViewById(R.id.memo_date);
                type = itemView.findViewById(R.id.memo_type);
                CheckBox = itemView.findViewById(R.id.checkbox);
                view = itemView;
            }
        }
    }
}
