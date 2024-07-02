package com.example.everest.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private int id;
    private String tenSp;
    private double giaCu;
    private double giaMoi;
    private String moTa;
    private String thongTinSp;
    private int tonKho;
    private String imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(int id,String tenSp, double giaCu, double giaMoi, String moTa, String thongTinSp, int tonKho, String imageUrl) {
        this.tenSp = tenSp;
        this.giaCu = giaCu;
        this.giaMoi = giaMoi;
        this.moTa = moTa;
        this.thongTinSp = thongTinSp;
        this.tonKho = tonKho;
        this.imageUrl = imageUrl;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public double getGiaCu() {
        return giaCu;
    }

    public void setGiaCu(double giaCu) {
        this.giaCu = giaCu;
    }

    public double getGiaMoi() {
        return giaMoi;
    }

    public void setGiaMoi(double giaMoi) {
        this.giaMoi = giaMoi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThongTinSp() {
        return thongTinSp;
    }

    public void setThongTinSp(String thongTinSp) {
        this.thongTinSp = thongTinSp;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
