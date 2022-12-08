package controller;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ndnha
 */
public class DatabaseHelper {    
    public static Connection openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=QLLinhKien;"
                    + "username=B1906622;password=12345678");
            return con;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }
}
