package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Film;
import com.example.project_1.Utility.Utility;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgViewFilm;
    TextView tv_namefilm, tv_phongchieu, tv_ghe, tv_total, tv_ticket;
    CheckBox ck_atm, ck_momo;
    Button btn_pay;
    int idfilm , maxc, giave;
    String tenphim, maphong, dsghe, tenphong;
    ArrayList<Integer> seatSelect;
    CreateAccount createAccount;
    Film film;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        createAccount = new CreateAccount( this, "Cinema.sqlite" , null , 1);

        anhxa();
        controlCheckbox();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("THANH TOÁN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();
        idfilm = data.getIntExtra("idfilm" , 0 );
        maxc = data.getIntExtra("maxuatchieu", 0);
        seatSelect = data.getIntegerArrayListExtra("seatSelect");
        maphong = data.getStringExtra("maphongchieu");

        String soghe = String.valueOf(seatSelect.size());
        tv_ticket.setText(soghe);
        Cursor laygiave = createAccount.GetPriceTicket();
        if(laygiave.moveToFirst()){
            do {
                int result = laygiave.getColumnIndex("DONGIA");
                giave = laygiave.getInt(result);
            } while ( laygiave.moveToNext());
        }
        String total = String.valueOf( seatSelect.size() * giave );
        tv_total.setText(total);

        Cursor room = createAccount.getRoom( maphong );
        if( room != null ){
            if( room.moveToFirst()){
                do{
                    tenphong = room.getString(room.getColumnIndex("TENPHONG"));
                    tv_phongchieu.setText(tenphong);
                } while (room.moveToNext());
            }
        }

        Cursor film = createAccount.getFilmById( idfilm);
        if( film != null ){
            if( film.moveToFirst()){
                do {
                    tenphim = film.getString(film.getColumnIndex("TENPHIM"));
                    tv_namefilm.setText(tenphim);
                } while ( film.moveToNext());
            }
        }
        byte[] imgfilm = createAccount.getBlobImageFilm(idfilm);
        if( imgfilm != null ){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgfilm, 0 , imgfilm.length);
            imgViewFilm.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Không có ảnh", Toast.LENGTH_SHORT).show();
        }
        String outputText = "";
        for( int seat : seatSelect){
            outputText += seat + ", ";
        }
        outputText = outputText.substring(0, outputText.length() - 2 );
        tv_ghe.setText(outputText);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility utility = (Utility) getApplication();
                int id = utility.getIdkh();
                if( id != 0 ){
                    if( !(ck_atm.isChecked()) && !(ck_momo.isChecked())){
                        Toast.makeText(PayActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                        return;
                    } else if( ck_atm.isChecked()){
                        Intent intent = new Intent(PayActivity.this, PayAtmActivity.class);
                        intent.putExtra("total", total);
                        intent.putExtra("maxc", maxc);
                        intent.putExtra("seatSelect", seatSelect);
                        startActivity(intent);

                    } else if( ck_momo.isChecked()){
                        Intent intent = new Intent( PayActivity.this, PayMomoAcitivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent( PayActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgViewFilm = (ImageView) findViewById(R.id.img_film_pay);
        tv_namefilm = (TextView) findViewById(R.id.tv_namefilm_pay);
        tv_phongchieu = (TextView) findViewById(R.id.tv_phongchieu);
        tv_ghe = (TextView) findViewById(R.id.tv_seat);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_ticket = (TextView) findViewById(R.id.tv_ticket);
        ck_atm = (CheckBox) findViewById(R.id.ck_atm);
        ck_momo = (CheckBox) findViewById(R.id.ck_momo);
        btn_pay = (Button) findViewById(R.id.btn_pay);
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

    private void controlCheckbox(){
        ck_atm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if( isChecked){
                ck_momo.setChecked(false);
            }
        });

        ck_momo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                ck_atm.setChecked(false);
            }
        });
    }

}