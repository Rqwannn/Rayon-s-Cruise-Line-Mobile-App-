package com.example.rayons.Model;

public class DataModel {
    private int id;
    private String nama_kapal, kelas_pelayaran, spesifikasi, gambar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }

    public String getKelas_pelayaran() {
        return kelas_pelayaran;
    }

    public void setKelas_pelayaran(String kelas_pelayaran) {
        this.kelas_pelayaran = kelas_pelayaran;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
