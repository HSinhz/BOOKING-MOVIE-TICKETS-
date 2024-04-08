package com.example.project_1.Model;

public class Ticket {
    private int IdTicket;
    private int MaXC;
    private int MaKHl;
    private String Seats;
    private byte[] QRVE;

    public Ticket(int idTicket, int maXC, int maKHl, String seats, byte[] qrve) {
        IdTicket = idTicket;
        MaXC = maXC;
        MaKHl = maKHl;
        Seats = seats;
        QRVE = qrve;
    }

    public int getIdTicket() {
        return IdTicket;
    }

    public void setIdTicket(int idTicket) {
        IdTicket = idTicket;
    }

    public int getMaXC() {
        return MaXC;
    }

    public void setMaXC(int maXC) {
        MaXC = maXC;
    }

    public int getMaKHl() {
        return MaKHl;
    }

    public void setMaKHl(int maKHl) {
        MaKHl = maKHl;
    }

    public String getSeats() {
        return Seats;
    }

    public byte[] getQRVE() {
        return QRVE;
    }

    public void setQRVE(byte[] QRVE) {
        this.QRVE = QRVE;
    }
}
