package com.example.project_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

public class UpdateFilmActivity extends AppCompatActivity {
    String tenPhim;
    String tluongPhim;
    String dinhdang;
    String theloai;
    String motaPhim;
    byte[] hinhanhPhim;
    int trangthaiPhim;
    public Button btnaddfilm;
    public ImageButton btnimgFolder;
    public EditText edtNameFilm, edtTimeFilm, edtDesc, edtTheloai, edtDinhdang;
    public CheckBox cbreferen, cbcoming , ckhide;
    public ImageView imgFilm;
    private int REQUEST_CODE_FOLDER = 456;
    int idfilm;
    CreateAccount createAccount;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_film);
        createAccount = new CreateAccount(this, "Cinema.sqlite" , null , 1);
        AnhXa();
        controlCheckbox();
        Intent intent = getIntent();
        idfilm = intent.getIntExtra("idfilm", 0);

        Cursor cursor = createAccount.getFilmById( idfilm);
        if( cursor.getCount() != 0 ){
            if( cursor.moveToFirst()){
                do{
                    tenPhim = cursor.getString(cursor.getColumnIndex("TENPHIM"));
                    tluongPhim = cursor.getString(cursor.getColumnIndex("THOIGIAN"));
                    theloai = cursor.getString(cursor.getColumnIndex("MATL"));
                    dinhdang = cursor.getString(cursor.getColumnIndex("MADD"));
                    motaPhim = cursor.getString(cursor.getColumnIndex("MOTA"));
                    trangthaiPhim = cursor.getInt(cursor.getColumnIndex("TRANGTHAI"));
                    if( trangthaiPhim == 1){
                        cbreferen.setChecked(true);
                    } else if( trangthaiPhim == 0){
                        cbcoming.setChecked(true);
                    }
                    hinhanhPhim = cursor.getBlob(cursor.getColumnIndex("HINHANH"));
                } while (cursor.moveToNext());
            }
        }

        setFilm(tenPhim, tluongPhim, theloai, dinhdang,motaPhim, trangthaiPhim, hinhanhPhim);


        btnimgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult( intent, REQUEST_CODE_FOLDER );
            }
        });

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

                boolean result = createAccount.UpdateFilm( idfilm, tenPhim , tluongPhim, dinhdang, theloai, motaPhim, trangthaiPhim ,hinhanh);
                if( result ){
                    Intent intent1 = new Intent(UpdateFilmActivity.this, ManagerFilmActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(UpdateFilmActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
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
        ckhide = (CheckBox) findViewById(R.id.ckHide);
    }

    private void controlCheckbox(){
        cbreferen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if( isChecked){
                cbcoming.setChecked(false);
                ckhide.setChecked(false);
            }
        });

        cbcoming.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cbreferen.setChecked(false);
                ckhide.setChecked(false);
            }
        });

        ckhide.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cbreferen.setChecked(false);
                cbcoming.setChecked(false);
            }
        });
    }

    private void setFilm( String tenPhim, String tluongPhim, String theloai, String dinhdang, String motaPhim, int trangthaiPhim, byte[] hinhanhPhim){
        edtNameFilm.setText(tenPhim);
        edtTimeFilm.setText(tluongPhim);
        edtTheloai.setText(theloai);
        edtDinhdang.setText(dinhdang);
        edtDesc.setText(motaPhim);
        if( trangthaiPhim == 1){
            cbreferen.setChecked(true);
        } else if( trangthaiPhim == 0){
            cbcoming.setChecked(true);
        }
        if( hinhanhPhim != null ){
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanhPhim, 0 , hinhanhPhim.length);
            imgFilm.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Không có ảnh", Toast.LENGTH_SHORT).show();
        }
    }
}