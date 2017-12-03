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
    private Button okBtn ;
    private Button clearBtn ;
    private EditText newKey ;
    private EditText key ;


    //得到SharedPreferences中的密码文件
    private SharedPreferences sharedPreferences;
    private String shareKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okBtn = (Button) super.findViewById(R.id.okBtn);
        clearBtn = (Button) super.findViewById(R.id.clearBtn);
        newKey = (EditText) findViewById(R.id.newKey);
        key = (EditText) findViewById(R.id.key);

        sharedPreferences = MainActivity.super.getSharedPreferences("passwordFile",MODE_PRIVATE);
        shareKey=sharedPreferences.getString("keyStr","");

        Toast.makeText(getApplicationContext(), shareKey, Toast.LENGTH_SHORT).show();

        resetUI();

        okBtn.setOnClickListener(new View.OnClickListener(){
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

        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                shareKey=sharedPreferences.getString("keyStr","");
                resetUI();
            }
        });

        final Button resetBtn =  (Button) super.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
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
        if(shareKey.equals("")){//第一次打开没有密码
            newKey.setVisibility(View.VISIBLE);//第一个输入框可见
            key.setText("");
            key.setHint(R.string.hint2);
            newKey.setText("");
            newKey.setHint(R.string.hint1);
        }
        else{//第二次打开有密码
            newKey.setVisibility(View.GONE);//第一个输入框不可见
            key.setText("");
            key.setHint(R.string.hint3);
            newKey.setText("");
            newKey.setHint(R.string.hint1);//更换提示内容
        }
    }


}
