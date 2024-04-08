package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.project_1.Fragment.PhimdangchieuFragment;

import java.util.PropertyPermission;

public class ManagerFilmActivity extends AppCompatActivity {

    private Button btnaddfilm, btnfilmreference, btnfilmcoming;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_film);

        anhxa();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("QUAY LẠi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnaddfilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerFilmActivity.this, AddFilmActivity.class);
                startActivity(intent);
            }
        });

        btnfilmreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhimdangchieuFragment fragment = new PhimdangchieuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("key", 1);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelistfilm, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnfilmcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhimdangchieuFragment fragment = new PhimdangchieuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("key", 2);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelistfilm, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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
    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        btnaddfilm = (Button) findViewById(R.id.btnaddfilm);
        btnfilmreference = (Button) findViewById(R.id.btnfilmreference);
        btnfilmcoming = (Button) findViewById(R.id.btnfilmcoming);
    }
}