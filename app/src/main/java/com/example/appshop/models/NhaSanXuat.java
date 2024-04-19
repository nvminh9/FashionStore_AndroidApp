package com.example.appshop.models;

public class NhaSanXuat {
    private String Logo;
    private int MaNSX;
    private String TenNSX;
    private String ThongTin;

    public NhaSanXuat(String logo, int maNSX, String tenNSX, String thongTin) {
        Logo = logo;
        MaNSX = maNSX;
        TenNSX = tenNSX;
        ThongTin = thongTin;
    }

    public String getLogo() {
        return "https://cougar-inviting-distinctly.ngrok-free.app/Asset/images/logo/" + Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public int getMaNSX() {
        return MaNSX;
    }

    public void setMaNSX(int maNSX) {
        MaNSX = maNSX;
    }

    public String getTenNSX() {
        return TenNSX;
    }

    public void setTenNSX(String tenNSX) {
        TenNSX = tenNSX;
    }

    public String getThongTin() {
        return ThongTin;
    }

    public void setThongTin(String thongTin) {
        ThongTin = thongTin;
    }
}
