package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;

public class SigninActivity extends AppCompatActivity {

    EditText edtusername, edtpass, edtconfirmpass , edtPhone, edtFullName;
    Button btnAccept;
    Spinner spSex;
    String username , password , fullname, phone;
    CreateAccount createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        anhxa();


        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);

//        createAccount.QueryData("CREATE TABLE IF NOT EXISTS Khachhang( id INTEGER PRIMARY KEY AUTOINCREMENT,  ");
        // Nút đăng kí
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }


    // Ánh xạ
    private void anhxa(){
        edtusername = (EditText) findViewById(R.id.edtUsername);
        edtpass = (EditText) findViewById(R.id.edtPass);
        edtconfirmpass = (EditText) findViewById(R.id.edtConfirmpass);
        edtFullName = (EditText) findViewById(R.id.edtfullName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnAccept = (Button) findViewById(R.id.btnAccept);
    }


    // Hàm xử lý đăng ký tài khoản
    private void register(){
        if(TextUtils.isEmpty( edtusername.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập Email hoặc số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty( edtpass.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty( edtconfirmpass.getText().toString())){
            Toast.makeText(this, "Vui lòng xác nhận lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty( edtFullName.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập Họ tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty( edtPhone.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        if( !( edtconfirmpass.getText().toString().equals( edtpass.getText().toString()) )){
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor dataAccount = createAccount.GetData("SELECT * FROM KhachHang WHERE TENDN =  '" +  edtusername.getText().toString() + "' ");
        if( dataAccount.getCount() > 0 ){
            Toast.makeText(this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }
        username = edtusername.getText().toString();
        password = edtpass.getText().toString();
        fullname = edtFullName.getText().toString();
        phone = edtPhone.getText().toString();
        String check = createAccount.InsertCustomer( fullname, phone, username, password);
//            Toast.makeText(this, check, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent( SigninActivity.this, MainActivity.class);
        startActivity( intent );
    }


}