package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.project_1.Adapter.BankAdapter;
import com.example.project_1.Fragment.PayAtmFragment;
import com.example.project_1.Utility.Utility;

import java.util.ArrayList;

public class PayAtmActivity extends AppCompatActivity implements PayAtmFragment.OnBackButtonClickListener {
    TextView tv_total;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Integer> imagesBank;
    FrameLayout frameLayout;
    int IdKH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_atm);

        anhxa();
        Intent data = getIntent();
        String tong = data.getStringExtra("total");
        int maxc = data.getIntExtra("maxc", 0);
        ArrayList<Integer> seatSelect = data.getIntegerArrayListExtra("seatSelect");
        tv_total.setText(tong);
        int numColumns = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numColumns);
        recyclerView.setLayoutManager(layoutManager);
        imagesBank = new ArrayList<>();
        imagesBank.add(R.drawable.tpbank);
        imagesBank.add(R.drawable.vietin);
        imagesBank.add(R.drawable.vietcombank);
        imagesBank.add(R.drawable.techcom);
        imagesBank.add(R.drawable.sacom);

        BankAdapter bankAdapter = new BankAdapter(this, imagesBank);
        recyclerView.setAdapter(bankAdapter);
        Utility utility = (Utility) getApplication();
        IdKH = utility.getIdkh();

        bankAdapter.setOnItemClickListener(new BankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int imageResId = imagesBank.get(position);

                PayAtmFragment fragment = new PayAtmFragment().newInstance(recyclerView);
                Bundle bundle = new Bundle();
                bundle.putInt("imageResId", imageResId );
                bundle.putInt("maxc", maxc );
                bundle.putInt("idkh", IdKH);
                bundle.putString("price",tong);
                bundle.putIntegerArrayList("seat" , seatSelect);
                fragment.setArguments(bundle);
                fragment.setOnBackButtonClickListener(PayAtmActivity.this);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_pay_atm, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("THANH TOÁN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_total = (TextView) findViewById(R.id.tv_total);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_bank);
        frameLayout = (FrameLayout) findViewById(R.id.fragment_pay_atm);

    }

    @Override
    public void onBackButtonClick() {
        getSupportFragmentManager().popBackStack();
    }
}