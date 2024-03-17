package com.example.appshop;

import java.io.Serializable;

public class SanPham implements Serializable {
    String TenSP, HinhAnh, MoTa, Loai;
    long Gia, Quantity;

    public SanPham() {
    }

    public SanPham(String tenSP, String hinhAnh, String moTa, String loai, long gia, long quantity) {
        TenSP = tenSP;
        HinhAnh = hinhAnh;
        MoTa = moTa;
        Loai = loai;
        Gia = gia;
        Quantity = quantity;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public long getGia() {
        return Gia;
    }

    public void setGia(long gia) {
        Gia = gia;
    }

    public long getQuantity() {
        return Quantity;
    }

    public void setQuantity(long quantity) {
        Quantity = quantity;
    }
}
