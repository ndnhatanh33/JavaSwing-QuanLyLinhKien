/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import model.LinhKien;

/**
 *
 * @author ndnha
 */
public class NhanhCanBalo {
    private ArrayList<LinhKien> dslk;
    private BigDecimal TienNhapVao, TienTaiNut;
    private float TongThongSo, GLNTT, CanTren;
    private int[] PhuongAn, x;
    private int n, soLuong;

    public NhanhCanBalo() {
    }

    public NhanhCanBalo(ArrayList<LinhKien> dslk, BigDecimal TienNhapVao, BigDecimal TienTaiNut, float CanTren, float TongThongSo, float GLNTT, int[] PhuongAn, int[] x) {
        this.dslk = dslk;
        this.TienNhapVao = TienNhapVao;
        this.TienTaiNut = TienTaiNut;
        this.CanTren = CanTren;
        this.TongThongSo = TongThongSo;
        this.GLNTT = GLNTT;
        this.PhuongAn = PhuongAn;
        this.x = x;
    }

    private void TinhDonGia () {
        int i;
        for (i=0;i<n;i++) {
            BigDecimal thongso = new BigDecimal(dslk.get(i).getThongSoCaoNhat());
            BigDecimal DG = thongso.divide(dslk.get(i).getGiaTien(),10,RoundingMode.HALF_UP);
            dslk.get(i).setDonGia(DG);
        }
    }

    private void BubbleSort(){
            int i,j;
            for(i=0; i<=n-2; i++)
               for (j=n-1; j>=i+1; j--){
                     if (dslk.get(j).getDonGia().compareTo(dslk.get(j-1).getDonGia())==1) {
                         LinhKien temp = dslk.get(j);
                         dslk.set(j, dslk.get((j-1)));
                         dslk.set(j-1, temp);
                     }
              }   
    }

    //Tinh cac dai luong tai cac nut goc
    private void Tao_Nut_Goc (BigDecimal TienNhap, BigDecimal DG_MAX) {
            TongThongSo = 0.0f;
            TienTaiNut = TienNhap;
            CanTren = TienTaiNut.multiply(DG_MAX).floatValue(); 
            GLNTT = 0.0f;
    }

    //Ghi nhan phuong an tot nhat tam thoi
    private void Cap_Nhat_GLNTT () {
            int i;
            if (GLNTT < TongThongSo) {
                    GLNTT = TongThongSo;
                    for (i=0;i<n;i++)
                            PhuongAn[i] = x[i];
            }
    }

    private void Nhanh_Can (int i) {
            int j;
            int yk;
            if (dslk.get(i).getSoLuong() < TienTaiNut.divide(dslk.get(i).getGiaTien(), 10, RoundingMode.HALF_UP).intValue())
                    yk = dslk.get(i).getSoLuong();
            else yk = TienTaiNut.divide(dslk.get(i).getGiaTien(), 10, RoundingMode.HALF_UP).intValue();
            for (j=yk;j>=0;j--) {

                    TongThongSo = TongThongSo + j*dslk.get(i).getThongSoCaoNhat();
                    TienTaiNut = TienTaiNut.subtract(new BigDecimal(j).multiply(dslk.get(i).getGiaTien()));
                    CanTren = TongThongSo + TienTaiNut.multiply(dslk.get(i+1).getDonGia()).floatValue();

                    if (CanTren > GLNTT) {
                            x[i] = j;
                            if (i==n-1 || TienTaiNut.equals(0))
                                    Cap_Nhat_GLNTT();
                            else
                                    Nhanh_Can(i+1);
                    }
                    x[i] = 0;
                    TongThongSo = TongThongSo - j*dslk.get(i).getThongSoCaoNhat();
                    TienTaiNut = TienTaiNut.add(new BigDecimal(j).multiply(dslk.get(i).getGiaTien()));
            }	
    }
    
    public ArrayList<LinhKien> TimPhuongAn (BigDecimal TienNhap, String maLoai) {
        TienNhapVao = TienNhap;
        LinhKienDao dao = new LinhKienDao();
        dslk = dao.findByMaLoai(maLoai);
        n = dslk.size();
        PhuongAn = new int[n];
        x = new int[n];
        dslk.add(new LinhKien("fix", "fix", "fix", "fix", 0.0f, new BigDecimal(0.0f), null, new BigDecimal(0.0f), 0));
        
        TinhDonGia();
        BubbleSort();
        Tao_Nut_Goc(TienNhapVao, dslk.get(0).getDonGia());
        Nhanh_Can(0);
        dslk.remove(dslk.size()-1);
        return dslk;
    }

    public int[] getPhuongAn() {
        return PhuongAn;
    }
    
    public float layTongThongSo () {
        float TTS = 0.0f;
        for (int i=0;i<dslk.size();i++) {
            if (PhuongAn[i] != 0) {
                TTS += PhuongAn[i]*dslk.get(i).getThongSoCaoNhat();
            }
        }
        return TTS;
    }
    
    public BigDecimal layTongGiaTien () {
        BigDecimal TT = new BigDecimal(0.0f);
        for (int i=0;i<dslk.size();i++) {
            if (PhuongAn[i] != 0) {
                TT = TT.add(new BigDecimal(PhuongAn[i]).multiply(dslk.get(i).getGiaTien()));
            }
        }
        return TT;
    }
    
}
