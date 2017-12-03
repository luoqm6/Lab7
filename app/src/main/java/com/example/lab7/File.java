package com.example.lab7;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static com.example.lab7.R.layout.file;

/**
 * Created by qingming on 2017/11/30.
 */

public class File extends AppCompatActivity {

    private Button save ;
    private Button load ;
    private Button clear;
    private Button delete;
    private EditText fileName;
    private EditText fileContent;
    private ArrayList<String> fileNameList = new ArrayList<>();

    //得到SharedPreferences中的所有文件
    private SharedPreferences sharedPreferences;
    private String shareContent;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        menu.add(Menu.NONE, Menu.NONE, 0,"所有文件如下：");


        //方法2：读取SharePreference中所有的文件名并且加入到menu中
//        loadArray();
//        for(int i=0;i<fileNameList.size();i++) {
//            menu.add(Menu.NONE, Menu.NONE, 1,fileNameList.get(i));
//        }

        //方法1：

        String[] files = this.fileList();
        for(String file : files){
            menu.add(Menu.NONE, Menu.NONE, 1,file);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //通过调用item.getItemId()来判断菜单项
        String itemStr = item.getTitle().toString();
        if(itemStr.equals("所有文件如下：")) return true;
        fileName.setText(itemStr);
        //方法2：读取SharePreference中文件名对应文件
//        shareContent=sharedPreferences.getString(fileName.getText().toString(),null);
//        if(shareContent!=null){
//            fileContent.setText(shareContent);
//            Toast.makeText(File.this,"Load successfully",Toast.LENGTH_SHORT).show();
//        }
        //方法1：读取file中文件名对应文件
        try (FileInputStream fileInputStream = openFileInput(fileName.getText().toString())) {
            byte[] contents = new byte[fileInputStream.available()];
            fileInputStream.read(contents);
            fileContent.setText(new String(contents));
            Toast.makeText(File.this,"Load successfully",Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex) {
            Log.e("TAG", "Fail to read file.");
            Toast.makeText(File.this,"Fail to load file!!!!!",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(file);
        sharedPreferences = File.super.getSharedPreferences("allFiles",MODE_PRIVATE);

        save = (Button) super.findViewById(R.id.save);
        load = (Button) super.findViewById(R.id.load);
        clear = (Button) super.findViewById(R.id.clear);
        delete = (Button) super.findViewById(R.id.delete);
        fileName = (EditText) findViewById(R.id.fileName);
        fileContent = (EditText) findViewById(R.id.fileContent);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方法2：将文件名对应文件存入SharePreference中
//                int size = sharedPreferences.getInt("fileNameListSize", 0);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("fileNameListSize",size+1);
//                editor.putString("fileName"+ String.valueOf(size),fileName.getText().toString());
//                editor.putString(fileName.getText().toString(),fileContent.getText().toString());
//                editor.commit();

                //方法1：存为file
                try (FileOutputStream fileOutputStream = openFileOutput(fileName.getText().toString(), MODE_PRIVATE)) {
                    fileOutputStream.write(fileContent.getText().toString().getBytes());
                    Toast.makeText(File.this,"Save successfully",Toast.LENGTH_SHORT).show();
                }
                catch (IOException ex) {
                    Toast.makeText(File.this,"Fail to save file.",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(File.this,"Save successfully",Toast.LENGTH_SHORT).show();
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方法2：读取SharePreference中文件名对应文件
//                shareContent=sharedPreferences.getString(fileName.getText().toString(),null);
//                if(shareContent==null){
//                    Toast.makeText(File.this,"Fail to load file!!!!!",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    fileContent.setText(shareContent);
//                    Toast.makeText(File.this,"Load successfully",Toast.LENGTH_SHORT).show();
//                }
                //方法1：读取file中文件名对应文件
                try (FileInputStream fileInputStream = openFileInput(fileName.getText().toString())) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    fileContent.setText(new String(contents));
                    Toast.makeText(File.this,"Load successfully",Toast.LENGTH_SHORT).show();
                }
                catch (IOException ex) {
                    Log.e("TAG", "Fail to read file.");
                    Toast.makeText(File.this,"Fail to load file!!!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileContent.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方法2：删除SharePreference中文件名对应文件
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove(fileName.getText().toString());
//                int size = sharedPreferences.getInt("fileNameListSize", 0);
//                for(int i=0;i<size;i++) {
//                    if(sharedPreferences.getString("fileName"+ String.valueOf(i), null).equals(fileName.getText().toString())){
//                        for(int j=i;j<size-1;j++){
//                            editor.putString("fileName"+ String.valueOf(j),sharedPreferences.getString("fileName"+String.valueOf(j+1), null));
//                            editor.commit();
//                        }
//                        editor.remove("fileName"+ String.valueOf(size-1));
//                        break;
//                    }
//                }
//                editor.putInt("fileNameListSize",size-1);
//                editor.commit();
//                Toast.makeText(File.this,"Delete successfully",Toast.LENGTH_SHORT).show();

                //方法1：删除file中文件名对应文件
                if(deleteFile(fileName.getText().toString())){
                    Toast.makeText(File.this,"Delete successfully",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(File.this,"Failed to delete file",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loadArray() {
        fileNameList.clear();
        int size = sharedPreferences.getInt("fileNameListSize", 0);
        for(int i=0;i<size;i++) {
            fileNameList.add(sharedPreferences.getString("fileName"+ String.valueOf(i), null));
        }
    }
}
