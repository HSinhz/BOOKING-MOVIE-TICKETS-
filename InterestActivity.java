package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Film;

import java.util.Calendar;

public class InterestActivity extends AppCompatActivity {
    Button btn830, btn1030 , btn1130, btn1245, btn1340, btn1425, btn1630, btn1745, btn1810, btn19, btn2145, btn2230, btn2315;
    TextView txtname, txttime;
    ImageView imgfilm;
    Toolbar toolbar;
    CreateAccount createAccount;
    int idfilm, maxuatchieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time);
        anhxa();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CHỌN XUẤT CHIẾU");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);

        Bundle bundle = getIntent().getExtras();
        if( bundle == null ){
            return;
        }
        Film film = (Film) bundle.get("film");

        byte[] hinhanh = film.getImgFilm();
        int idfilm = film.getIdFilm();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0 , hinhanh.length);
        imgfilm.setImageBitmap(bitmap);
        txtname.setText(film.getNameFilm());
        txttime.setText(film.getTimeFilm());

        OnClick(btn830, film);
        OnClick(btn1030, film);
        OnClick(btn1130, film);
//        OnClick(btn1245, film);
//        OnClick(btn1340, film);
//        OnClick(btn1425, film);
//        OnClick(btn1630, film);
//        OnClick(btn1745, film);
//        OnClick(btn1810, film);
//        OnClick(btn19, film);
//        OnClick(btn2145, film);
//        OnClick(btn2230, film);
//        OnClick(btn2315, film);


        Calendar currentTime = Calendar.getInstance();
        setTime(8, 30, btn830 ,currentTime , idfilm);
        setTime(24, 05, btn1030 ,currentTime, idfilm );
        setTime(11, 30, btn1130 ,currentTime, idfilm );
        setTime(12, 45, btn1245 ,currentTime, idfilm );
        setTime(13, 40, btn1340 ,currentTime, idfilm );
        setTime(14, 25, btn1425 ,currentTime, idfilm );
        setTime(16, 30, btn1630 ,currentTime, idfilm );
        setTime(17, 45, btn1745 ,currentTime, idfilm );
        setTime(18, 10, btn1810 ,currentTime, idfilm );
        setTime(19, 0, btn19 ,currentTime, idfilm );
        setTime(21, 45, btn2145 ,currentTime, idfilm );
        setTime(22, 30, btn2230 ,currentTime, idfilm );
        setTime(23, 15, btn2315 ,currentTime, idfilm );


    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgfilm = (ImageView) findViewById(R.id.imageView3);
        txtname = (TextView) findViewById(R.id.txt_name_film);
        txttime = (TextView) findViewById(R.id.txt_time_film);
        btn830 = (Button) findViewById(R.id.btn830);
        btn1030 = (Button) findViewById(R.id.btn10);
        btn1130 = (Button) findViewById(R.id.btn1130);
        btn1245 = (Button) findViewById(R.id.btn1245);
        btn1340 = (Button) findViewById(R.id.btn1340);
        btn1425 = (Button) findViewById(R.id.btn1425);
        btn1630 = (Button) findViewById(R.id.btn1630);
        btn1745 = (Button) findViewById(R.id.btn1745);
        btn1810 = (Button) findViewById(R.id.btn1810);
        btn19 = (Button) findViewById(R.id.btn19);
        btn2145 = (Button) findViewById(R.id.btn2145);
        btn2230 = (Button) findViewById(R.id.btn2230);
        btn2315 = (Button) findViewById(R.id.btn2315);
    }

    private void setTime( int hour, int minute, Button btn , Calendar currentTime, int idfilm) {
        Calendar sosanh = Calendar.getInstance();
        sosanh.set(Calendar.HOUR_OF_DAY, hour);
        sosanh.set(Calendar.MINUTE, minute);
        sosanh.set(Calendar.SECOND, 0);

        if( currentTime.after(sosanh)){
            btn.setVisibility(View.GONE);
            String giochieu = btn.getText().toString();
            Cursor cursor = createAccount.GetData("SELECT * FROM XUATCHIEU WHERE MAPHIM == " + idfilm + " AND GIOCHIEU = '" + giochieu + "'" );
            if( cursor.moveToFirst()){
                do {
                    @SuppressLint("Range") String MAPC = cursor.getString(cursor.getColumnIndex("MAPC"));
                    int result = cursor.getColumnIndex("MAXC");
                    maxuatchieu = cursor.getInt(result);
                } while (cursor.moveToNext());
            }
            createAccount.XoaXuatChieu( maxuatchieu);
        }
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

    private void OnClick( Button btn , Film film){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String giochieu = btn.getText().toString();
                Intent intent = new Intent(InterestActivity.this, SeatSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("film", film );
                intent.putExtras( bundle );
                intent.putExtra("giochieu", giochieu);
                startActivity( intent);
            }
        });
    }

}