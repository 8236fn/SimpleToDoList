package com.blogspot.tiaotone.simpletodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvTitle;
    private EditText editMemo;
    private Button btnOk,btnBack;
    private Spinner spColor;
    private String newMemo,currentTime;
    private SpinnerAdapter spinnerAdapter;
    private DbAdapter dbAdapter;
    public Bundle bundle;
    public int index;
    public String selectedColor;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        dbAdapter = new DbAdapter(this);
        bundle = this.getIntent().getExtras();
        if (bundle.getString("type").equals("edit")){
            tvTitle.setText("編輯內容");
            index = bundle.getInt("itemId");
            Cursor cursor = dbAdapter.queryById(index);
            editMemo.setText(cursor.getString(2));
        }
    }
    private void initView(){
        tvTitle = findViewById(R.id.tvTitle);
        editMemo = findViewById(R.id.editMemo);
        editMemo.setOnClickListener(this);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        spColor = findViewById(R.id.spColor);
        final ArrayList<ItemData> colorList = new ArrayList<ItemData>();
        colorList.add(new ItemData("bright_pink","#FF007F"));
        colorList.add(new ItemData("red","#FF0000"));
        colorList.add(new ItemData("orange","#FF7F00"));
        colorList.add(new ItemData("yellow","#FFFF00"));
        colorList.add(new ItemData("chartreuse","#7FFF00"));
        colorList.add(new ItemData("green","#00FF00"));
        colorList.add(new ItemData("spring_green","#00FF7F"));
        colorList.add(new ItemData("cyan","#00FFFF"));
        colorList.add(new ItemData("azure","#007FFF"));
        colorList.add(new ItemData("blue","#0000FF"));
        colorList.add(new ItemData("violet","#7F00FF"));
        colorList.add(new ItemData("magenta","#FF00FF"));
        spinnerAdapter = new SpinnerAdapter(this,colorList);
        spColor.setAdapter(spinnerAdapter);
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView colorTicket = (view.findViewById(R.id.colorTicket));
                ColorDrawable drawable = (ColorDrawable) colorTicket.getBackground();
                selectedColor = Integer.toHexString(drawable.getColor()).substring(2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editMemo:
                if(bundle.getString("type").equals("add"))
                    editMemo.setText("");
                break;
            case R.id.btnOk:
                newMemo = editMemo.getText().toString();
                currentTime = dateFormat.format(new Date(System.currentTimeMillis()));
                if(bundle.getString("type").equals("edit")){
                    try{
                        dbAdapter.updateMemo(index,currentTime,newMemo,selectedColor);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                    }
                }else {
                    try{
                        dbAdapter.createMemo(currentTime,newMemo,selectedColor);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.btnBack:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
        }
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(bundle.getString("type").equals("edit")){
            menuInflater.inflate(R.menu.edit_menu,menu);
        }產生menuitem
        return super.onCreateOptionsMenu(menu);
    }
    //產生menuitem的刪除功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(this);
                builder.setTitle("警告")
                        .setMessage("刪除後無法回復！確定要刪除嗎？")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Boolean isDeleted = dbAdapter.deleteMemo(index);
                                if(isDeleted) {
                                    Toast.makeText(EditActivity.this, "資料已經刪除", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
