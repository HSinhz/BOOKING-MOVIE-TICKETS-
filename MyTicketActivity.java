package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.project_1.Adapter.TicketAdapter;
import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Ticket;
import com.example.project_1.Utility.Utility;

import java.util.ArrayList;

public class MyTicketActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv_ticket;
    private ArrayList<Ticket> arrayList;
    private TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        CreateAccount createAccount = new CreateAccount( this, "Cinema.sqlite" , null, 1);
        anhxa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("VÉ CỦA TÔI");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<>();
        ticketAdapter = new TicketAdapter( this, R.layout.row_ticket, arrayList);
        lv_ticket.setAdapter(ticketAdapter);

        Utility utility = (Utility) getApplication();
        int idkh = utility.getIdkh();
        Cursor cursor = createAccount.getTicketbyId( idkh );
        if( cursor.getCount() != 0 ){
            while (cursor.moveToNext()){
                arrayList.add( new Ticket(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                ));
            }
            ticketAdapter.notifyDataSetChanged();
        }

    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lv_ticket = (ListView) findViewById(R.id.lv_ticket);
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