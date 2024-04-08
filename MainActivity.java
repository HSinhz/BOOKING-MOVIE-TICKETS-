package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_1.Adapter.FilmAdapter;
import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Film;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int session = 0, idfilm;
    String username, HelloName;
    TextView txtHello;
    ImageView img_account, img_my_ticket;
    RecyclerView recyclerView, recyclerViewComing;
    Button btnlogin, btnaddfilm;
    CreateAccount createAccount;
    FilmAdapter filmAdapter, filmComingAdapter;
    ArrayList<Film> filmArrayList, filmComingArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);
        anhXa();



        // Lấy dữ liệu trong intent của Activity Login
        Intent data = getIntent();
        session = data.getIntExtra("sessionlogin" , 0);
        username = data.getStringExtra("hello");


        // Ẩn nút đăng nhập nếu đã đăng nhập rồi
        if( session == 1 ){
            getHelloName(); // Nếu đăng nhập r mới thực hiện hàm
            btnlogin.setVisibility(View.GONE);
        }

        GetFilmRefernce();
        GetFilmComingSoon();

        // Sự kiện vào trang đăng nhập
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ;

        img_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( session == 0 ){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent( MainActivity.this, AccountActivity.class );
                    intent.putExtra("name", username);
                    startActivity(intent);
                }

            }
        });

        img_my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( session == 0 ){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent( MainActivity.this, MyTicketActivity.class );
                    startActivity(intent);
                }
            }
        });

    }

    // Khai báo các thành phần layout có sử dúng
    private void anhXa(){
        recyclerView = (RecyclerView) findViewById(R.id.rcv_film);
        recyclerViewComing = (RecyclerView) findViewById(R.id.rcv_filmcoming);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        txtHello = (TextView) findViewById(R.id.txtHello);
        img_account = (ImageView) findViewById(R.id.img_account);
        img_my_ticket = (ImageView) findViewById(R.id.img_my_ticket);
    }

    // Lấy Họ và Tên cảu khách hàng
    @SuppressLint("Range")
    public void getHelloName(){
        Cursor cursor = createAccount.GetData("SELECT HOTEN FROM KhachHang WHERE TENDN = '" + username + "'");
        if( cursor.moveToFirst()){
            do {
                HelloName = cursor.getString(cursor.getColumnIndex("HOTEN"));
                txtHello.setText("Xin chào: " + HelloName);
            } while (cursor.moveToNext());
        }
    }


    // Lấy các phim đang chiếu
    public void GetFilmRefernce(){
        filmArrayList = new ArrayList<>();
        filmAdapter = new FilmAdapter(this, filmArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(filmAdapter);
        Cursor dataFilm = createAccount.GetData("SELECT * FROM Phim WHERE TRANGTHAI == 1");
        while ( dataFilm.moveToNext() ){
            filmArrayList.add( new Film(
                    dataFilm.getInt(0),
                    dataFilm.getString(1),
                    dataFilm.getString(2),
                    dataFilm.getString(3),
                    dataFilm.getString(4),
                    dataFilm.getString(5),
                    dataFilm.getDouble(6),
                    dataFilm.getBlob(7)
            ));
        }
        filmAdapter.notifyDataSetChanged();
    }

    // Lấy các phim sắp chiếu
    private void GetFilmComingSoon(){
        filmComingArrayList = new ArrayList<>();
        filmComingAdapter = new FilmAdapter(this, filmComingArrayList );
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewComing.setLayoutManager(layoutManager1);
        recyclerViewComing.setAdapter(filmComingAdapter);
        Cursor dataFilm1 = createAccount.GetData("SELECT * FROM Phim WHERE TRANGTHAI == 0");
        while ( dataFilm1.moveToNext()){
            filmComingArrayList.add( new Film(
                    dataFilm1.getInt(0),
                    dataFilm1.getString(1),
                    dataFilm1.getString(2),
                    dataFilm1.getString(3),
                    dataFilm1.getString(4),
                    dataFilm1.getString(5),
                    dataFilm1.getDouble(6),
                    dataFilm1.getBlob(7)
            ));
            idfilm = dataFilm1.getInt(0);
        }
        filmComingAdapter.notifyDataSetChanged();
    }


}
