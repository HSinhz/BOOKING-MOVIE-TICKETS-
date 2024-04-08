package com.example.project_1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Ticket;
import com.example.project_1.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends BaseAdapter {
    public Context mContext;
    public int mLayout;
    public List<Ticket> mList;
    public TicketAdapter( Context context, int layout, List<Ticket> list){
        this.mContext = context;
        this.mLayout = layout;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgqrcode;
        TextView tv_name, tv_time, tv_phongchieu, tv_ghe;

    }

    @SuppressLint("Range")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CreateAccount createAccount = new CreateAccount( mContext, "Cinema.sqlite", null, 1 );
        ViewHolder viewHolder;
        if( convertView == null ){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, null);

            viewHolder.imgqrcode = ( ImageView) convertView.findViewById(R.id.img_qr);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_phongchieu = (TextView) convertView.findViewById(R.id.tv_phongchieu);
            viewHolder.tv_ghe = (TextView) convertView.findViewById(R.id.tv_ghe);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = mList.get(position);
        byte[] qrcode = ticket.getQRVE();
        Bitmap bitmap = BitmapFactory.decodeByteArray(qrcode,0, qrcode.length);
        viewHolder.imgqrcode.setImageBitmap(bitmap);
        viewHolder.tv_ghe.setText(ticket.getSeats());
        int idxc = ticket.getMaXC();
        int idfilm = 0;
        String mapc = "";
        Cursor cursor = createAccount.GetXC(idxc);
        if( cursor.getCount() != 0 ){
            while ( cursor.moveToNext()){
                idfilm = cursor.getInt(cursor.getColumnIndex("MAPHIM"));
                mapc = cursor.getString(cursor.getColumnIndex("MAPC"));
                viewHolder.tv_time.setText(cursor.getString(cursor.getColumnIndex("GIOCHIEU")));
            }
        }

        Cursor getfilm = createAccount.getFilmById(idfilm);
        if( getfilm.getCount() != 0 ){
            while (getfilm.moveToNext()){
                viewHolder.tv_name.setText(getfilm.getString(getfilm.getColumnIndex("TENPHIM")));
            }
        }

        Cursor getRoom = createAccount.getRoom( mapc);
        if( getRoom.getCount() != 0 ){
            while (getRoom.moveToNext()){
                viewHolder.tv_phongchieu.setText(getRoom.getString(getRoom.getColumnIndex("TENPHONG")));
            }
        }

        return convertView;
    }
}
