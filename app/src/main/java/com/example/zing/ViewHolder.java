package com.example.zing;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// Kế thừa 2 sự kiện click và long click
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{
    ImageView imgSong;
    TextView txtNameSong;
    private OnItemClickListener listener;

    //Tạo setter lắng nghe sự kiện
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        imgSong = itemView.findViewById(R.id.imgSong);
        txtNameSong = itemView.findViewById(R.id.txtNameSong);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }
    //Sự kiện click
    @Override
    public void onClick(View view) {
        if (listener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position);
            }
        }
    }
    //Sự kiện long click
    @Override
    public boolean onLongClick(View view) {
        listener.onItemLongClick(getAdapterPosition());
        return true;
    }
}