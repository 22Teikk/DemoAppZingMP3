package com.example.zing;

public class Song {
    private String name;
    private int song, img;
    //Constructor
    public Song(String name, int song, int img) {
        this.name = name;
        this.song = song;
        this.img = img;
    }
    //Setter and getter
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }
}
