package com.example.zing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity{
    RecyclerView rcvSong;
    ArrayList<Song> listSong;
    AdapterSong adapterSong;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWidget();
        rcvSong.setLayoutManager(new LinearLayoutManager(this));
        rcvSong.setAdapter(adapterSong);
    }

    //Ánh xạ
    private void getWidget(){
        rcvSong = findViewById(R.id.rcvSong);
        listSong = new ArrayList<>();
        listSong.add(new Song("Tháng tư là lời nói dối của em", R.raw.hat, R.drawable.hat));
        listSong.add(new Song("Yêu em rất nhiều",R.raw.ht, R.drawable.ht));
        listSong.add(new Song("Dưới những cơn mưa",R.raw.mrs, R.drawable.mrs));
        listSong.add(new Song("Day dứt nỗi đau",R.raw.mrsr, R.drawable.mrsr));
        listSong.add(new Song("Như phút ban đầu",R.raw.noo, R.drawable.noo));
        intent = getIntent();
        adapterSong = new AdapterSong(this, listSong, intent);
    }

    //Cài đặt menu khi chọn xong nhạc
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.choose_song, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Bắt sự kiện quay về phát nhạc khi chọn vào menu thu gọn
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_choose){
            setResult(MainActivity.REQUEST_CODE_SONG, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}