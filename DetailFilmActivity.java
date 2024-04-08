package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

public class DetailFilmActivity extends AppCompatActivity {
    String theloai;
    Toolbar toolbar;
    ImageView imgfilm;
    TextView tv_Name, tv_Time, tv_Type, tv_Desc;
    CreateAccount createAccount;
    Button btn_book_ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);


        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);
        anhxa();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CHI TIẾT PHIM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if( bundle == null ){
            return;
        }

        Film film = (Film) bundle.get("film");
        byte[] hinhanh = film.getImgFilm();
        int idfilm = film.getIdFilm();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0 , hinhanh.length);
        imgfilm.setImageBitmap(bitmap);
        tv_Name.setText(film.getNameFilm());
        tv_Time.setText(film.getTimeFilm());
        tv_Type.setText(film.getKindFilm());
        theloai = film.getKindFilm();
        getNameKindFilm( theloai );
        tv_Desc.setText(film.getDescFilm());
        if( film.getStatusFilm() == 0 ){
            btn_book_ticket.setVisibility(View.GONE);
        }

        btn_book_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( DetailFilmActivity.this, InterestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("film", film );
                intent.putExtras( bundle );
                startActivity( intent);
            }
        });
    }

    @SuppressLint("Range")
    private void getNameKindFilm(String theloai) {
        String tentheloai;
        Cursor cursor = createAccount.GetData("SELECT TENTL FROM THELOAI WHERE MATL = '" + theloai + "'");
        if( cursor.moveToFirst()){
            do {
                tentheloai = cursor.getString(cursor.getColumnIndex("TENTL"));
                tv_Type.setText(tentheloai);
            } while (cursor.moveToNext());
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

    private void anhxa() {
        imgfilm = (ImageView) findViewById(R.id.img_film_detail);
        tv_Name = (TextView) findViewById(R.id.tv_namefilm);
        tv_Time = (TextView) findViewById(R.id.tv_time);
        tv_Type = (TextView) findViewById(R.id.tv_type);
        tv_Desc = ( TextView) findViewById(R.id.tv_desc);
        btn_book_ticket = (Button) findViewById(R.id.btn_book_ticket);
        toolbar =  (Toolbar) findViewById(R.id.toolbar);
    }
}