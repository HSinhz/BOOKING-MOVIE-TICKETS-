package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Utility.Utility;

public class LoginActivity extends AppCompatActivity {
    int sessionLogin = 0, role, idkhachang, check;
    String username, pass, hoten;
    CreateAccount createAccount;
    EditText edtusername, edtpass;
    CheckBox ckRemeber;
    Button btnlogin ,btnsignin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);

        anhxa();
        XulyLuuDN();

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this , SigninActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginControl();
                if( sessionLogin == 1){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("sessionlogin", sessionLogin);
                    intent.putExtra("hello", username);
                    intent.putExtra("role", role);
                    startActivity( intent);
                }

            }
        });
    }


    private void anhxa(){
        edtusername = (EditText) findViewById(R.id.edtUsername);
        edtpass = (EditText) findViewById(R.id.edtPassword) ;
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnsignin = (Button) findViewById(R.id.btnsignin);
        ckRemeber = (CheckBox) findViewById(R.id.ckRemember);
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
    }

    @SuppressLint("Range")
    private void LoginControl(){
        if(TextUtils.isEmpty( edtusername.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập Email hoặc số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty( edtpass.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        username = edtusername.getText().toString();
        pass = edtpass.getText().toString();
        Cursor cursor = createAccount.CheckLogin( username , pass);
        if( cursor.getCount() != 0 ){
            check = 1;
            if( cursor.moveToFirst()){
                do {
                    role = cursor.getInt(cursor.getColumnIndex("ROLE"));
                    idkhachang = cursor.getInt(cursor.getColumnIndex("MAKH"));
                } while (cursor.moveToNext());
            }
        }

        if( check != 0  ){
            sessionLogin = 1;

            Utility utility = (Utility) getApplication();
            utility.setIdkh( idkhachang );
            utility.setRole( role );
            utility.setSession( sessionLogin );
            Log.d("makh " , String.valueOf( utility.getIdkh()) );
            if( ckRemeber.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("pass", pass);
                editor.putBoolean("checked", true);
                editor.commit();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("pass");
                editor.remove("checked");
                editor.commit();
            }
//          Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void XulyLuuDN(){
        edtusername.setText(sharedPreferences.getString("username", null));
        edtpass.setText(sharedPreferences.getString("pass", null));
        ckRemeber.setChecked(sharedPreferences.getBoolean("checked", false));
    }



}