package com.example.project_1.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.R;
import com.example.project_1.TicketActivity;
import com.example.project_1.Utility.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayAtmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayAtmFragment extends Fragment {
    private EditText edtnumberCard, edtDate, edtOwn;
    private Button btnback , btnaccept;
    private ImageView imagebank;
    RecyclerView recyclerView;
    int imageResId, maxc , IdKhachHang;
    String total;
    ArrayList<Integer> seatSelect;
    Utility utility;

    public PayAtmFragment() {
        // Required empty public constructor
    }

    public interface OnBackButtonClickListener{
        void onBackButtonClick();
    }

    private OnBackButtonClickListener listener;

    public void setOnBackButtonClickListener( OnBackButtonClickListener listener){
        this.listener = listener;
    }

    public static PayAtmFragment newInstance(RecyclerView recyclerViews) {
        PayAtmFragment fragment = new PayAtmFragment();
        fragment.setRecyclerView(recyclerViews);
        return fragment;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView.setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_pay_atm2, container, false);
        Bundle args = getArguments();

        if( args != null ){
            imageResId  = args.getInt("imageResId");
            maxc = args.getInt("maxc");
            seatSelect = args.getIntegerArrayList("seat");
            total = args.getString("price");
            IdKhachHang = args.getInt("idkh");
        }

        CreateAccount createAccount = new CreateAccount( getContext(), "Cinema.sqlite" , null , 1);
        anhxa( view);



        Log.d("ma khach hang" , String.valueOf(IdKhachHang));
        imagebank.setImageResource(imageResId);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( listener != null ){
                    listener.onBackButtonClick();
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtnumberCard.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập số thẻ", Toast.LENGTH_SHORT).show();
                    return;
                } else if( edtDate.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập ngày phát hành", Toast.LENGTH_SHORT).show();
                    return;
                } else if( edtOwn.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập Tên Chủ Thẻ", Toast.LENGTH_SHORT).show();
                    return;
                }

                byte[] blob = new byte[10];
                String outputText = "";
                for( int seat : seatSelect){
                    outputText += seat + ", ";
                    createAccount.InsertSeat( seat, 1, maxc );
                }
                outputText = outputText.substring(0, outputText.length() - 2 );
                createAccount.InsertTicket( maxc, IdKhachHang, outputText, blob);

                int idve = createAccount.GetTicket(maxc, IdKhachHang, outputText);
                if( idve == 0 ){
                    Toast.makeText(getContext(), "Không có vé", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar currentTime = Calendar.getInstance();
                String dataTicket = String.valueOf(idve) + String.valueOf(maxc) + String.valueOf(IdKhachHang) + String.valueOf(currentTime);
                // Tạo mã QR từ dữ liệu
                Bitmap bitmap = generateQRCode(dataTicket);


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
                byte[] QRcodeTicket = byteArrayOutputStream.toByteArray();
                createAccount.UpdateQRcodeTicket( QRcodeTicket, idve );


                Intent intent = new Intent(getContext(), TicketActivity.class);
                intent.putExtra("mave", idve);
                intent.putExtra("total", total);
                startActivity(intent);
                if( getActivity() != null ){
                    getActivity().finish();
                }

            }
        });
        return view;
    }

    private void anhxa( View view) {

        imagebank = view.findViewById(R.id.img_bank);
        btnback = view.findViewById(R.id.btnback);
        btnaccept = view.findViewById(R.id.btn_accept);
        edtnumberCard = view.findViewById(R.id.edtNumberCard);
        edtDate = view.findViewById(R.id.edtDate);
        edtOwn = view.findViewById(R.id.edtName);
    }

    private Bitmap generateQRCode(String data) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1); // Đặt margin cho mã QR

        try {
            bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512, hints);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
            }
        }

        return bmp;
    }
}