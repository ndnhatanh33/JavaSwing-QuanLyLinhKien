/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author ndnha
 */
public class LinhKien {
    private String MaSoLinhKien, MaLoai, TenLinhKien, ThongTin;
    private float ThongSoCaoNhat;
    private BigDecimal GiaTien;
    private byte[] HinhAnh;
    private BigDecimal DonGia;
    private int SoLuong;

    public BigDecimal getDonGia() {
        return DonGia;
    }

    public void setDonGia(BigDecimal DonGia) {
        this.DonGia = DonGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public LinhKien() {
    }

    public LinhKien(String MaSoLinhKien, String MaLoai, String TenLinhKien, String ThongTin, float ThongSoCaoNhat, BigDecimal GiaTien, byte[] HinhAnh, BigDecimal DonGia, int SoLuong) {
        this.MaSoLinhKien = MaSoLinhKien;
        this.MaLoai = MaLoai;
        this.TenLinhKien = TenLinhKien;
        this.ThongTin = ThongTin;
        this.ThongSoCaoNhat = ThongSoCaoNhat;
        this.GiaTien = GiaTien;
        this.HinhAnh = HinhAnh;
        this.DonGia = DonGia;
        this.SoLuong = SoLuong;
    }

    public String getMaSoLinhKien() {
        return MaSoLinhKien;
    }

    public void setMaSoLinhKien(String MaSoLinhKien) {
        this.MaSoLinhKien = MaSoLinhKien;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(String MaLoai) {
        this.MaLoai = MaLoai;
    }

    public String getTenLinhKien() {
        return TenLinhKien;
    }

    public void setTenLinhKien(String TenLinhKien) {
        this.TenLinhKien = TenLinhKien;
    }

    public String getThongTin() {
        return ThongTin;
    }

    public void setThongTin(String ThongTin) {
        this.ThongTin = ThongTin;
    }

    public float getThongSoCaoNhat() {
        return ThongSoCaoNhat;
    }

    public void setThongSoCaoNhat(float ThongSoCaoNhat) {
        this.ThongSoCaoNhat = ThongSoCaoNhat;
    }

    public BigDecimal getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(BigDecimal GiaTien) {
        this.GiaTien = GiaTien;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
}
