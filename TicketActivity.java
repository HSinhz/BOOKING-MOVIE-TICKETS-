package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.database.CursorWindowCompat;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Utility.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class TicketActivity extends AppCompatActivity {

    ImageView imgFilm;
    ImageView imgQRCode;
    TextView tv_namefilm, tv_time, tv_seat, tv_price;
    Button btnhome;
    CreateAccount createAccount;
    int maxc, maphim;
    String ghe,giochieu, tenphim;
    byte[] qrcode, imgfilm;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        anhxa();

        Intent data = getIntent();
        int idve = data.getIntExtra("mave", 0);
        String total = data.getStringExtra("total");
        tv_price.setText(total);
        Cursor cursor = createAccount.GetTicketbyId( idve );
        if( cursor != null ){
            if( cursor.moveToFirst()){
                do{
                    int result = cursor.getColumnIndex("MAXC");
                    maxc = cursor.getInt(result);
                    ghe = cursor.getString(cursor.getColumnIndex("GHE"));
                    tv_seat.setText(ghe);
                    qrcode = cursor.getBlob(cursor.getColumnIndex("QRVE"));
                }while (cursor.moveToNext());
            }

        }

        Cursor xuatchieu = createAccount.GetXC( maxc );
        if( xuatchieu != null ){
            if( xuatchieu.moveToFirst()){
                do {
                    giochieu = xuatchieu.getString(xuatchieu.getColumnIndex("GIOCHIEU"));
                    tv_time.setText(giochieu);
                    maphim = xuatchieu.getInt(xuatchieu.getColumnIndex("MAPHIM"));
                }while (xuatchieu.moveToNext());
            }

        }


        Cursor film = createAccount.getFilmById(maphim);
        if( film != null ){
            if( film.moveToFirst()){
                do {
                    tenphim = film.getString(film.getColumnIndex("TENPHIM"));
                    tv_namefilm.setText(tenphim);
                    imgfilm = film.getBlob(film.getColumnIndex("HINHANH"));
                } while (film.moveToNext());
            }
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(imgfilm, 0 , imgfilm.length);
        imgFilm.setImageBitmap(bitmap);

        Bitmap qr = BitmapFactory.decodeByteArray(qrcode, 0 , qrcode.length);
        imgQRCode.setImageBitmap(qr);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility utility = (Utility) getApplication();
                int session = utility.getSession();
                Intent intent = new Intent(TicketActivity.this, MainActivity.class);
                intent.putExtra( "sessionlogin", session);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        createAccount = new CreateAccount( this, "Cinema.sqlite", null, 1);
        imgFilm = (ImageView) findViewById(R.id.img_film);
        imgQRCode = (ImageView) findViewById(R.id.qr_code);
        tv_namefilm = (TextView) findViewById(R.id.tv_namefilm);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_seat = (TextView) findViewById(R.id.tv_seats);
        tv_price = (TextView) findViewById(R.id.tv_price);
        btnhome = (Button) findViewById(R.id.btnhome);
    }


}