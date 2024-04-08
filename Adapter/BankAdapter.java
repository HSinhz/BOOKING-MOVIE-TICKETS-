package com.example.project_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1.R;

import java.util.ArrayList;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    private Context mContex;
    private ArrayList<Integer> mImages;

    public BankAdapter( Context context, ArrayList<Integer> images){
        this.mContex = context;
        this.mImages = images;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_bankk,parent, false);
        return new BankViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
        final int imgResource = mImages.get(position);
        holder.imgBank.setImageResource(imgResource);

        holder.imgBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null ) {
                    mListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class BankViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBank;
        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBank = itemView.findViewById(R.id.imgview_bank);
        }
    }



}
