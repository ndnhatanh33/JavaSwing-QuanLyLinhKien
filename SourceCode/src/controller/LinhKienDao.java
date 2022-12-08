/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.serial.SerialBlob;
import model.LinhKien;

/**
 *
 * @author ndnha
 */
public class LinhKienDao {

    public boolean insert(LinhKien lk) {
        try {
            String sql = "INSERT INTO [dbo].[LinhKien] ([MaSoLinhKien],[MaLoai],[TenLinhKien],"
                    + "[ThongTin],[ThongSoCaoNhat],[GiaTien],[HinhAnh],[SoLuong])"
                    + " values(?,?,?,?,?,?,?,?)";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, lk.getMaSoLinhKien());
            ps.setString(2, lk.getMaLoai());
            ps.setString(3, lk.getTenLinhKien());
            ps.setString(4, lk.getThongTin());
            ps.setDouble(5, lk.getThongSoCaoNhat());
            ps.setBigDecimal(6, lk.getGiaTien());
            if (lk.getHinhAnh() != null) {
                Blob hinh = new SerialBlob(lk.getHinhAnh());
                ps.setBlob(7, hinh);
            } else {
                Blob hinh = null;
                ps.setBlob(7, hinh);
            }
            ps.setInt(8, lk.getSoLuong());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(LinhKien lk) {
        try {
            String sql = "UPDATE dbo.LinhKien SET TenLinhKien=?, ThongTin=?, ThongSoCaoNhat=?, GiaTien=?, HinhAnh=?, SoLuong=?"
                    + " WHERE MaSoLinhKien = ? AND MaLoai = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, lk.getTenLinhKien());
            ps.setString(2, lk.getThongTin());
            ps.setDouble(3, lk.getThongSoCaoNhat());
            ps.setBigDecimal(4, lk.getGiaTien());
            if (lk.getHinhAnh() != null) {
                Blob hinh = new SerialBlob(lk.getHinhAnh());
                ps.setBlob(5, hinh);
            } else {
                Blob hinh = null;
                ps.setBlob(5, hinh);
            }
            ps.setInt(6, lk.getSoLuong());
            ps.setString(7, lk.getMaSoLinhKien());
            ps.setString(8, lk.getMaLoai());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maLinhKien, String maLoai) {
        try {
            String sql = "delete from linhkien"
                    + " WHERE [MaSoLinhKien] = ? AND [MaLoai] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maLinhKien);
            ps.setString(2, maLoai);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public LinhKien findByMaSoLinhKien_MaLoai (String maLinhKien, String maLoai) {
        try {
            String sql = "select * from linhkien"
                    + " WHERE [MaSoLinhKien] = ? AND [MaLoai] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maLinhKien);
            ps.setString(2, maLoai);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LinhKien lk = creatLinhKien(rs);
                return lk;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LinhKien> findByMaLoai (String maLoai) {
        try {
            String sql = "select * from linhkien"
                    + " WHERE [MaLoai] = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maLoai);

            ResultSet rs = ps.executeQuery();
            ArrayList<LinhKien> list = new ArrayList<>();
            while (rs.next()) {
                LinhKien lk = creatLinhKien(rs);
                list.add(lk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private LinhKien creatLinhKien(ResultSet rs) throws SQLException {
        LinhKien lk = new LinhKien();
        lk.setMaSoLinhKien(rs.getString("MaSoLinhKien"));
        lk.setMaLoai(rs.getString("MaLoai"));
        lk.setTenLinhKien(rs.getString("TenLinhKien"));
        lk.setThongTin(rs.getString("ThongTin"));
        lk.setThongSoCaoNhat(rs.getFloat("ThongSoCaoNhat"));
        lk.setGiaTien(rs.getBigDecimal("GiaTien"));
        Blob blob = rs.getBlob("HinhAnh");
        if (blob != null)
            lk.setHinhAnh(blob.getBytes(1, (int)blob.length()));
        lk.setSoLuong(rs.getInt("SoLuong"));
        return lk;
    }
}
