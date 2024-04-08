package com.example.project_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFilmActivity extends AppCompatActivity {
    String tenPhim, tluongPhim , dinhdang, theloai ,motaPhim, hinhanhPhim;
    int trangthaiPhim;
    public Button btnaddfilm;
    public ImageButton btnimgFolder;
    public EditText edtNameFilm, edtTimeFilm, edtDesc, edtTheloai, edtDinhdang;
    public CheckBox cbreferen, cbcoming;
    public ImageView imgFilm;
    private int REQUEST_CODE_FOLDER = 456;
    CreateAccount createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        AnhXa();

        createAccount = new CreateAccount(this, "Cinema.sqlite" , null , 1);
        btnimgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult( intent, REQUEST_CODE_FOLDER );
            }
        });

        controlCheckbox();
        btnaddfilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển data ImageView sang mảng byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgFilm.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] hinhanh = byteArrayOutputStream.toByteArray();

                tenPhim = edtNameFilm.getText().toString().trim();
                tluongPhim = edtTimeFilm.getText().toString().trim();
                dinhdang = edtDinhdang.getText().toString().trim();
                theloai = edtTheloai.getText().toString().trim();
                motaPhim = edtDesc.getText().toString().toString();
                if( cbreferen.isChecked()){
                    trangthaiPhim = 1;
                } else if( cbcoming.isChecked()){
                    trangthaiPhim = 0;
                }
                boolean result = createAccount.InsertFilm(tenPhim, tluongPhim, dinhdang, theloai, motaPhim, trangthaiPhim ,hinhanh);
                if( result){
                    Intent intent = new Intent(AddFilmActivity.this, ManagerFilmActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddFilmActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgFilm.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa() {
        btnaddfilm = (Button) findViewById(R.id.btnAddFilm);
        edtNameFilm = (EditText) findViewById(R.id.edtNameFilm);
        edtTimeFilm = (EditText) findViewById(R.id.edtFilTime);
        edtDinhdang = (EditText) findViewById(R.id.edtDinhdang);
        edtTheloai = (EditText) findViewById(R.id.edtTheloai);
        edtDesc = (EditText) findViewById(R.id.edtDesc);
        imgFilm = (ImageView) findViewById(R.id.imgFilm);
        btnimgFolder = (ImageButton) findViewById(R.id.imgbtnFolder);
        cbcoming = (CheckBox) findViewById(R.id.cbcomingsoon);
        cbreferen = (CheckBox) findViewById(R.id.cbreferencing);
    }

    private void controlCheckbox(){
        cbreferen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if( isChecked){
                cbcoming.setChecked(false);
            }
        });

        cbcoming.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cbreferen.setChecked(false);
            }
        });
    }

}