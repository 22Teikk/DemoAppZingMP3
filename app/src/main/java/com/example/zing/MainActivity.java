package com.example.zing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView btnPlay, btnPrev, btnStop, btnNext, imgDvd, btnMenu;
    TextView txtTile, txtTimeTotal, txtTimeSong;
    SeekBar seekBar;
    ArrayList<Song> listSong;
    int song;
    int pos = 0;
    int pos_song = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    public static final int REQUEST_CODE_SONG = 123;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    //Give and receive data Intent
    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultCode) {
                    int result = resultCode.getResultCode();
                    Intent data = resultCode.getData();
                    if (result == REQUEST_CODE_SONG){
                        song = data.getIntExtra("file_song", 0);
                        pos_song = data.getIntExtra("pos_song", 0);
                        listSong.remove(pos_song);
                        for (int i = 0; i < listSong.size(); i++)
                            if (song == listSong.get(i).getSong())
                                pos = i;
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        setSong();
                        setTimeTotal();
                        setTimeSong();
                        mediaPlayer.start();
                        imgDvd.startAnimation(animation);
                        btnPlay.setImageResource(R.drawable.pause);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWidget();
        addSong();
        btnMenu.setOnClickListener(new ExcuteButton());
        btnPlay.setOnClickListener(new ExcuteButton());
        btnNext.setOnClickListener(new ExcuteButton());
        btnStop.setOnClickListener(new ExcuteButton());
        btnPrev.setOnClickListener(new ExcuteButton());
        txtTile.setText(listSong.get(pos).getName());
        mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(pos).getSong());
        setTimeSong();
        setTimeTotal();
        //Hàm sử lý seek bar khi thay đổi
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //Di chuyển tới đâu thay đổi tới đó
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }
            //Di chuyển tời điểm chạm
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //Di chuyển tới điểm dừng
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        animation = AnimationUtils.loadAnimation(this, R.anim.dvd_song);
    }



    //Lấy thời gian của bài hát
    private void setTimeSong(){
        txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void setTimeTotal(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        pos++;
                        if (pos > listSong.size() - 1)
                            pos = 0;
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        setSong();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        setTimeSong();
                        setTimeTotal();
                    }
                });
                handler.postDelayed(this, 500);

            }
        }, 100);
    }

    //Khời tạo bài hát
    private void setSong(){
        txtTile.setText(listSong.get(pos).getName());
        mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(pos).getSong());
    }

    private class ExcuteButton implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnMenu){
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name", txtTile.getText().toString());
                arl.launch(intent);
            }
            if (view.getId() == R.id.imgPlay) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                    imgDvd.clearAnimation();
                } else{
                    imgDvd.startAnimation(animation);
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
            }
            if (view.getId() == R.id.imgStop) {
                mediaPlayer.stop();
                setSong();
                btnPlay.setImageResource(R.drawable.pause);
                imgDvd.clearAnimation();

            }
            if (view.getId() == R.id.imgNext){
                pos++;
                if (pos > listSong.size() - 1)
                    pos = 0;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                setSong();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeSong();
                setTimeTotal();
                imgDvd.startAnimation(animation);
            }
            if (view.getId() == R.id.imgPrev){
                pos--;
                if (pos < 0)
                    pos = listSong.size() - 1;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                setSong();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeSong();
                setTimeTotal();
                imgDvd.startAnimation(animation);
            }
        }
    }

    private void getWidget(){
        txtTile = findViewById(R.id.txtTitle);
        txtTimeTotal = findViewById(R.id.txtTimeTotal);
        txtTimeSong = findViewById(R.id.txtTimeSong);
        btnPlay = findViewById(R.id.imgPlay);
        btnNext = findViewById(R.id.imgNext);
        btnStop = findViewById(R.id.imgStop);
        btnPrev = findViewById(R.id.imgPrev);
        seekBar = findViewById(R.id.seekBar);
        imgDvd = findViewById(R.id.imgDvd);
        btnMenu = findViewById(R.id.btnMenu);
    }

    private void addSong(){
        listSong = new ArrayList<>();
        listSong.add(new Song("Tháng tư là lời nói dối của em", R.raw.hat, R.drawable.hat));
        listSong.add(new Song("Yêu em rất nhiều",R.raw.ht, R.drawable.ht));
        listSong.add(new Song("Dưới những cơn mưa",R.raw.mrs, R.drawable.mrs));
        listSong.add(new Song("Day dứt nỗi đau",R.raw.mrsr, R.drawable.mrsr));
        listSong.add(new Song("Như phút ban đầu",R.raw.noo, R.drawable.noo));
    }


}