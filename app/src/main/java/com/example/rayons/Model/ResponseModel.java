package com.example.rayons.Model;

import java.util.List;

public class ResponseModel {
    private int Kode;
    private String Pesan, PesanEmail, PesanUsername, PesanPassword;
    private List<DataModel> Data;

    public int getKode() {
        return Kode;
    }

    public void setKode(int kode) {
        Kode = kode;
    }

    public String getPesan() {
        return Pesan;
    }

    public void setPesan(String pesan) {
        Pesan = pesan;
    }

    public List<DataModel> getData() {
        return Data;
    }

    public void setData(List<DataModel> data) {
        Data = data;
    }

    public String getPesanPassword() {
        return PesanPassword;
    }

    public void setPesanPassword(String pesanPassword) {
        PesanPassword = pesanPassword;
    }

    public String getPesanUsername() {
        return PesanUsername;
    }

    public void setPesanUsername(String pesanUsername) {
        PesanUsername = pesanUsername;
    }

    public String getPesanEmail() {
        return PesanEmail;
    }

    public void setPesanEmail(String pesanEmail) {
        PesanEmail = pesanEmail;
    }
}
