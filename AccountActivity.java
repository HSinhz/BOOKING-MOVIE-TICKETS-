package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_1.Utility.Utility;

public class AccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnaddmin, btnlogout;
    TextView tv_name, tv_role;
    String manager = "Quản trị viên";
    String customer = "Khách hàng";
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        anhxa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TÀI KHOẢN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();
        tv_name.setText( data.getStringExtra("name"));
        Utility utility = (Utility) getApplication();
        role = utility.getRole();
        if( role == 0 ){
            btnaddmin.setVisibility(View.GONE);
            tv_role.setText(customer);
        } else {
            tv_role.setText(manager);
        }

        btnaddmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, ManagerFilmActivity.class);
                startActivity(intent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnaddmin = (Button) findViewById(R.id.btnaddmin);
        tv_role = (TextView) findViewById(R.id.tv_role);
        tv_name = (TextView) findViewById(R.id.tv_name);
        btnlogout = (Button) findViewById(R.id.btnlogout);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish(); // hoặc thực hiện các xử lý bạn muốn khi nút back được nhấn
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}