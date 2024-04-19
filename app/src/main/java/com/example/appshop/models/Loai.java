package com.example.appshop.models;

public class Loai {

    private String Loai;
    private String Hinhanh;

    public Loai() {
    }

    public Loai(String loai, String hinhanh) {
        Loai = loai;
        Hinhanh = hinhanh;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return "Loai{" +
                "Loai='" + Loai + '\'' +
                ", Hinhanh='" + Hinhanh + '\'' +
                '}';
    }
}
