package com.example.project_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_1.DetailFilmActivity;
import com.example.project_1.Model.Film;
import com.example.project_1.R;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmVieHolder> {

    Context context;
    ArrayList<Film> arrayfilm;
    public FilmAdapter( Context context, ArrayList<Film> arrayfilm){
        this.context = context;
        this.arrayfilm = arrayfilm;
    }
    @NonNull
    @Override
    public FilmVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_film, parent, false);
        return new FilmVieHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmVieHolder holder, int position) {
        final Film film = arrayfilm.get(position);
        byte[] hinhanh = film.getImgFilm();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0 , hinhanh.length);
        holder.imgFilm.setImageBitmap(bitmap);

        holder.imgFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(film);
            }
        });
    }

    private void onClickGoToDetail(Film film) {
        Intent intent = new Intent( context, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", film );
        intent.putExtras( bundle );
        context.startActivity( intent);
    }

    @Override
    public int getItemCount() {
        return arrayfilm.size();
    }

    public class FilmVieHolder extends RecyclerView.ViewHolder {
        ImageView imgFilm;
        public FilmVieHolder(@NonNull View itemView) {
            super(itemView);
            imgFilm = itemView.findViewById(R.id.img_film);
        }
    }
}
