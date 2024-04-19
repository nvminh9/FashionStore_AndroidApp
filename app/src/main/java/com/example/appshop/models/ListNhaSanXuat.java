package com.example.appshop.models;

import java.util.ArrayList;
import java.util.List;

public class ListNhaSanXuat {
    private ArrayList<NhaSanXuat> listnhasanxuat;

    public ListNhaSanXuat(ArrayList<NhaSanXuat> listnhasanxuat) {
        this.listnhasanxuat = listnhasanxuat;
    }

    public ArrayList<NhaSanXuat> getListnhasanxuat() {
        return listnhasanxuat;
    }

    public void setListnhasanxuat(ArrayList<NhaSanXuat> listnhasanxuat) {
        this.listnhasanxuat = listnhasanxuat;
    }
}
