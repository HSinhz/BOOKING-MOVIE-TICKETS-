package com.example.project_1.Model;

import java.io.Serializable;

public class Film implements Serializable {
    private int idFilm;
    private String nameFilm;
    private String timeFilm;
    private String indentifyFilm;
    private String kindFilm;
    private String descFilm;
    private double statusFilm;
    private byte[] imgFilm;
    public Film(int maFilm, String tenFilm, String thoiluongFilm, String dinhdangFilm, String theloaiFilm, String motaFilm, double trangthaiFilm, byte[] hinhanhFilm  ){
        idFilm = maFilm;
        nameFilm = tenFilm;
        timeFilm = thoiluongFilm;
        indentifyFilm = dinhdangFilm;
        kindFilm = theloaiFilm;
        descFilm = motaFilm;
        statusFilm = trangthaiFilm;
        imgFilm = hinhanhFilm;
    }
    public int getIdFilm(){
        return idFilm;
    }
    public void setIdFilm( int maPhim)
    {
        idFilm = maPhim;
    }
    public String getNameFilm()
    {
        return nameFilm;
    }
    public void setNameFilm( String tenPhim )
    {
        nameFilm = tenPhim;
    }
    public String getTimeFilm()
    {
        return timeFilm;
    }
    public void setTimeFilm( String thoiluongPhim)
    {
        timeFilm = thoiluongPhim;
    }
    public String getIndentifyFilm(){
        return indentifyFilm;
    }
    public void setIndentifyFilm( String dinhdangPhim ){
        indentifyFilm = dinhdangPhim;
    }
    public String getKindFilm(){
        return kindFilm;
    }
    public void setKindFilm( String theloaiPhim ){
        kindFilm = theloaiPhim;
    }
    public String getDescFilm(){
        return descFilm;
    }
    public void setDescFilm( String motaPhim ){
        descFilm = motaPhim;
    }

    public double getStatusFilm( ){
        return statusFilm;
    }
    public void setStatusFilm( double trangthaiFilm){
        statusFilm = trangthaiFilm;
    }
    public byte[] getImgFilm(){
        return imgFilm;
    }
    public void setImgFilm( byte[] hinhanhPhim ){
        imgFilm = hinhanhPhim;
    }
}
