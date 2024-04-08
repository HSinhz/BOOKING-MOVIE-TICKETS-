package com.example.project_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.Database.CreateAccount;
import com.example.project_1.ManagerFilmActivity;
import com.example.project_1.Model.Film;
import com.example.project_1.R;
import com.example.project_1.UpdateFilmActivity;

import java.util.List;

public class ManagerFilmAdapter extends BaseAdapter {
    Context mContext;
    int mlayout;
    List<Film> mListFilm;

    public ManagerFilmAdapter( Context context, int layout ,List<Film> filmList){
        this.mContext = context;
        this.mlayout = layout;
        this.mListFilm = filmList;
    }
    @Override
    public int getCount() {
        return mListFilm.size();
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
        TextView tvnamefilm, tvtimefilm, tvkindfilm;
        ImageView imgFilm, imgdelete, imgupdate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if( convertView == null ){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mlayout, null);

            viewHolder.tvnamefilm = (TextView) convertView.findViewById(R.id.tv_namefilm);
            viewHolder.tvkindfilm = (TextView) convertView.findViewById(R.id.tv_kind);
            viewHolder.tvtimefilm = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.imgFilm = (ImageView) convertView.findViewById(R.id.img_film);
            viewHolder.imgdelete = (ImageView) convertView.findViewById(R.id.img_delete);
            viewHolder.imgupdate = (ImageView) convertView.findViewById(R.id.img_update);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)  convertView.getTag();
        }
        Film film = mListFilm.get(position);
        int id = film.getIdFilm();
        byte[] hinhanh = film.getImgFilm();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0 , hinhanh.length );
        viewHolder.imgFilm.setImageBitmap(bitmap);
        viewHolder.tvnamefilm.setText(film.getNameFilm());
        viewHolder.tvtimefilm.setText(film.getTimeFilm());
        viewHolder.tvkindfilm.setText(film.getKindFilm());

        viewHolder.imgupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( mContext, UpdateFilmActivity.class);
                intent.putExtra("idfilm", id);
                mContext.startActivity(intent);
            }
        });

        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount createAccount = new CreateAccount( mContext, "Cinema.sqlite" , null , 1);
                boolean result = createAccount.DeleteFilm( id);
                if( result == true ){
                    boolean kq = createAccount.deleteXuatChieuPhim( id );
                    if( result == true){
                        Intent intent = new Intent( mContext, ManagerFilmActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "Xóa xuất chiếu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
}
