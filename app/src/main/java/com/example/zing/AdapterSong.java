package com.example.zing;//package com.example.demozingmp3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSong extends RecyclerView.Adapter<ViewHolder>{
    Context context;
    ArrayList<Song> listSong;
    private OnItemClickListener listener;
    Intent intent;
    //Tạo constructor
    public AdapterSong(Context context, ArrayList<Song> listSong, Intent intent) {
        this.context = context;
        this.listSong = listSong;
        this.intent = intent;
    }
    public AdapterSong(Context context, ArrayList<Song> listSong, OnItemClickListener listener) {
        this.context = context;
        this.listSong = listSong;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_song,parent, false);
        ViewHolder holder = new ViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = listSong.get(position);
        holder.imgSong.setImageResource(song.getImg());
        holder.txtNameSong.setText(song.getName());
        //Bắt sự kiện click
        holder.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(context, listSong.get(position).getName(), Toast.LENGTH_SHORT).show();
                intent.putExtra("file_song", listSong.get(position).getSong());

            }

            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Notification!");
                dialog.setMessage("Do you want remove this song?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listSong.remove(position);
                        intent.putExtra("pos_song", position);
                        notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }


}
