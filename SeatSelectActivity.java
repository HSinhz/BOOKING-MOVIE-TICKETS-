package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SeatSelectActivity extends AppCompatActivity {
    GridLayout gridLayout;
    TextView txt_name_film, txt_price, txt_quantity;
    Button seat, btn_book;
    String giochieu, MAPC;
    CreateAccount createAccount;
    int idfilm, soluongghe, maxuatchieu;
    int soghedangchon, giave, giavedangchon, demsoghe;
    ArrayList<Integer> selectSeats;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);
        anhxa();
        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);
        Bundle bundle = getIntent().getExtras();
        if( bundle == null ){
            return;
        }
        Film film = (Film) bundle.get("film");
        idfilm = film.getIdFilm();
        Intent data = getIntent();
        giochieu = data.getStringExtra("giochieu");
        txt_name_film.setText(film.getNameFilm());
        @SuppressLint("Range")
        Cursor cursor = createAccount.GetData("SELECT * FROM XUATCHIEU WHERE MAPHIM == " + idfilm + " AND GIOCHIEU = '" + giochieu + "'" );
        if( cursor.moveToFirst()){
            do {
                MAPC = cursor.getString(cursor.getColumnIndex("MAPC"));
                int result = cursor.getColumnIndex("MAXC");
                maxuatchieu = cursor.getInt(result);
            } while (cursor.moveToNext());
        }
        Cursor cursor1 = createAccount.GetSeat(MAPC);
        if(cursor1.moveToFirst() ){
            do {
                int result = cursor1.getColumnIndex("SOLUONGGHE");
                soluongghe = cursor1.getInt(result);
                Log.d("SeatSelectActivity" ,"So luong ghe " + soluongghe );
            } while (cursor1.moveToNext());
        }

        Cursor laygiave = createAccount.GetPriceTicket();
        if(laygiave.moveToFirst()){
            do {
                int result = laygiave.getColumnIndex("DONGIA");
                giave = laygiave.getInt(result);
            } while ( laygiave.moveToNext());
        }
        selectSeats = new ArrayList<>();
        taoghe( soluongghe);

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soghedangchon == 0 ){
                    Toast.makeText(SeatSelectActivity.this, "Vui lòng chọn ghế", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent( SeatSelectActivity.this, PayActivity.class);
                    intent.putExtra("idfilm", idfilm);
                    intent.putExtra("maxuatchieu", maxuatchieu);
                    intent.putExtra("maphongchieu", MAPC);
                    Collections.sort(selectSeats);
                    intent.putIntegerArrayListExtra("seatSelect", selectSeats);
                    startActivity(intent);
                }
            }
        });
    }

    private void anhxa(){
        gridLayout = (GridLayout) findViewById(R.id.grid_seat);
        seat = (Button) findViewById(R.id.seat);
        txt_name_film = (TextView) findViewById(R.id.txt_name_film);
        txt_price = (TextView) findViewById(R.id.txt_price);
        txt_quantity = (TextView) findViewById(R.id.txt_quantity);
        btn_book = (Button) findViewById(R.id.btn_book_seat);
    }

    private void taoghe( int soluongghe ){
        for( int i = 1; i <= soluongghe ; i++ ){
            final int seatNumber = i;
            if (seat.getParent() != null) {
                ((ViewGroup) seat.getParent()).removeView(seat);
            }
            Button seat = new Button(this);
            int height = seat.getHeight();
            seat.setWidth(height);
            seat.setText(String.valueOf(i));
            seat.setLayoutParams(new GridLayout.LayoutParams());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;

            seat.setLayoutParams(params);
            seat.setTextColor(Color.WHITE);
            params.setMargins(4,4,4,4);
            seat.setBackgroundDrawable(getResources().getDrawable(R.drawable.baseline_chair_24));
            gridLayout.addView(seat, new GridLayout.LayoutParams(new ViewGroup.LayoutParams(140,140)));
            int kiemtra = createAccount.GetStatusSeat(maxuatchieu, i);
            if( kiemtra == 1 ){
                kiemtra = 0;
                seat.setBackgroundDrawable(getResources().getDrawable(R.drawable.baseline_chair_24_grey));
            } else {
                seat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (seat.isSelected()) {
                            demsoghe--;
                            seat.setSelected(false);
                            seat.setBackgroundDrawable(getResources().getDrawable(R.drawable.baseline_chair_24));
                            soghedangchon--;
                            selectSeats.remove(Integer.valueOf(seatNumber));
                            giavedangchon = giave * soghedangchon;
                            txt_quantity.setText(soghedangchon + " ghế");
                            txt_price.setText(giavedangchon + "đ");
                        } else {
                            demsoghe++;
                            seat.setSelected(true);
                            seat.setBackgroundDrawable(getResources().getDrawable(R.drawable.baseline_chair_24_red));
                            soghedangchon++;
                            selectSeats.add(seatNumber);
                            giavedangchon = giave * soghedangchon;
                            txt_quantity.setText(soghedangchon + " ghế" );
                            txt_price.setText(giavedangchon + "đ");
                        }
                    }
                });
            }
        }
    }
}