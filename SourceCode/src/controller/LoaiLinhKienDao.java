/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.LoaiLinhKien;

/**
 *
 * @author ndnha
 */
public class LoaiLinhKienDao {
    public boolean insert (LoaiLinhKien llk) {
        try {
            String sql = "INSERT INTO [dbo].[LoaiLinhKien] ([MaLoai], [TenLoai]) values(?,?)";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, llk.getMaLoai());
            ps.setString(2, llk.getTenLoai());

            return ps.executeUpdate()>0;
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
        
    public boolean update (LoaiLinhKien llk) {
        try {
            String sql = "UPDATE dbo.LoaiLinhKien" +
                    " SET TenLoai = ?" +
                    " where MaLoai = ?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, llk.getTenLoai());
            ps.setString(2, llk.getMaLoai());

            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete (String maLoai) {
        try {
            String sql = "delete from LoaiLinhKien where MaLoai=?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maLoai);
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public LoaiLinhKien findByMaLoai (String maLoai) {
        try {
            String sql = "select * from LoaiLinhKien where Maloai=?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maLoai);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoaiLinhKien llk = createLoaiLinhKien(rs);
                return llk;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public LoaiLinhKien findByTenLoai (String tenLoai) {
        try {
            String sql = "select * from LoaiLinhKien where Tenloai=?";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenLoai);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoaiLinhKien llk = createLoaiLinhKien(rs);
                return llk;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<LoaiLinhKien> findAll () {
        try {
            String sql = "select * from LoaiLinhKien";
            Connection con = DatabaseHelper.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<LoaiLinhKien> list = new ArrayList<>();
            while (rs.next()) {  
                LoaiLinhKien llk = createLoaiLinhKien(rs);
                list.add(llk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private LoaiLinhKien createLoaiLinhKien(ResultSet rs) throws SQLException {
        LoaiLinhKien llk = new LoaiLinhKien();
        llk.setMaLoai(rs.getString("maloai"));
        llk.setTenLoai(rs.getString("tenloai"));
        return llk;
    }
}
