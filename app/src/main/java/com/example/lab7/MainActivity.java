package com.example.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //要用到的控件
    private Button ok ;
    private Button clear ;
    private EditText newKey ;
    private EditText key ;


    //得到SharedPreferences中的密码文件
    private SharedPreferences sharedPreferences;
    private String shareKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ok = (Button) super.findViewById(R.id.ok);
        clear = (Button) super.findViewById(R.id.clear);
        newKey = (EditText) findViewById(R.id.newKey);
        key = (EditText) findViewById(R.id.key);

        sharedPreferences = MainActivity.super.getSharedPreferences("passwordFile",MODE_PRIVATE);
        shareKey=sharedPreferences.getString("keyStr","");

        Toast.makeText(getApplicationContext(), shareKey, Toast.LENGTH_SHORT).show();

        resetUI();

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String newKeyStr =  newKey.getText().toString();
                String keyStr =  key.getText().toString();
                if(TextUtils.isEmpty(keyStr)){
                    Toast.makeText(MainActivity.this,"Password cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(shareKey.equals("")){
                        if(newKeyStr.equals(keyStr)){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("keyStr",keyStr);
                            editor.commit();
                            Intent intent =new Intent(MainActivity.this,File.class);
                            startActivityForResult(intent,1);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Password Mismatch",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        if(shareKey.equals(keyStr)){
                            Intent intent =new Intent(MainActivity.this,File.class);
                            startActivityForResult(intent,1);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Password Mismatch",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                shareKey=sharedPreferences.getString("keyStr","");
                resetUI();
            }
        });

        final Button reset =  (Button) super.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                shareKey=sharedPreferences.getString("keyStr","");
                resetUI();
            }
        });

    }

    public void resetUI(){
        //根据内容判断是否需要新密码框
        if(shareKey.equals("")){
            newKey.setVisibility(View.VISIBLE);
            key.setText("");
            key.setHint(R.string.hint2);
            newKey.setText("");
            newKey.setHint(R.string.hint1);
        }
        else{
            newKey.setVisibility(View.GONE);
            key.setText("");
            key.setHint(R.string.hint3);
            newKey.setText("");
            newKey.setHint(R.string.hint1);
        }
    }


}
