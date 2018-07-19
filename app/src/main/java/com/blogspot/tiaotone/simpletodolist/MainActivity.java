package com.blogspot.tiaotone.simpletodolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView memoList;
    private TextView noMemos;
    private Intent intent;
    private DbAdapter dbAdapter;
    private ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memoList = findViewById(R.id.memolist);
        noMemos = findViewById(R.id.noMemos);
        dbAdapter = new DbAdapter(this);

        if (dbAdapter.listshow().getCount() == 0 ){
            memoList.setVisibility(View.INVISIBLE);
            noMemos.setVisibility(View.VISIBLE);
        }else {
            memoList.setVisibility(View.VISIBLE);
            noMemos.setVisibility(View.INVISIBLE);
        }
        displayList();
    }
    public void displayList(){
        Cursor cursor = dbAdapter.listshow();
        listAdapter = new ListAdapter(this,cursor);
        memoList.setAdapter(listAdapter);
        memoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor itemCursor = (Cursor) memoList.getItemAtPosition(position);
                int itemId = itemCursor.getInt(itemCursor.getColumnIndexOrThrow("_id"));
                intent = new Intent();
                intent.putExtra("itemId",itemId);
                intent.putExtra("type","edit");
                intent.setClass(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(this,EditActivity.class);
                i.putExtra("type","add");
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
