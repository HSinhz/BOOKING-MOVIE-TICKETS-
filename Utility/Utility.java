package com.example.project_1.Utility;

import android.app.Application;

public class Utility extends Application {
    private int Idkh;
    private int role;
    private int session;
    public int getIdkh(){
        return Idkh;
    }
    public void setIdkh( int idkh){
        this.Idkh =  idkh;
    }

    public int getRole(){
        return role;
    }
    public void setRole( int role ){
        this.role = role;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }
}
