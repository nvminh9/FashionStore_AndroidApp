package com.example.appshop;

import java.io.Serializable;

public class Cart implements Serializable {
    String TenSP;
    long Gia, CartQuantity;
    String HinhAnh;
    String currentDate,currentTime;

    public Cart() {
    }

    public Cart(String tenSP, long gia, long cartQuantity, String hinhAnh, String currentDate, String currentTime) {
        TenSP = tenSP;
        Gia = gia;
        CartQuantity = cartQuantity;
        HinhAnh = hinhAnh;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public long getGia() {
        return Gia;
    }

    public void setGia(long gia) {
        Gia = gia;
    }

    public long getCartQuantity() {
        return CartQuantity;
    }

    public void setCartQuantity(long cartQuantity) {
        CartQuantity = cartQuantity;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
