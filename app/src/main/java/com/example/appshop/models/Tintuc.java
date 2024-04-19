package com.example.appshop.models;

public class Tintuc {

    private String MaTintuc;
    private String Tieude, motangan, tintucimg;

    public Tintuc() {
    }

    public Tintuc(String maTintuc, String tieude, String motangan, String tintucimg) {
        MaTintuc = maTintuc;
        Tieude = tieude;
        this.motangan = motangan;
        this.tintucimg = tintucimg;
    }

    public String getMaTintuc() {
        return MaTintuc;
    }

    public void setMaTintuc(String maTintuc) {
        MaTintuc = maTintuc;
    }

    public String getTieude() {
        return Tieude;
    }

    public void setTieude(String tieude) {
        Tieude = tieude;
    }

    public String getMotangan() {
        return motangan;
    }

    public void setMotangan(String motangan) {
        this.motangan = motangan;
    }

    public String getTintucimg() {
        return tintucimg;
    }

    public void setTintucimg(String tintucimg) {
        this.tintucimg = tintucimg;
    }

    @Override
    public String toString() {
        return "Tintuc{" +
                "MaTintuc='" + MaTintuc + '\'' +
                ", Tieude='" + Tieude + '\'' +
                ", motangan='" + motangan + '\'' +
                ", tintucimg='" + tintucimg + '\'' +
                '}';
    }
}
