/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInfo;

import cbb.API;
import cbb.Schools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author danielhiggins
 */
public class User {

    API a;
    private String username, password, team;
    Date s = new Date();
    Calendar c;

    public User(String u, String p, String t) {
        username = u;
        password = p;
        team = t;
    }

    public User() {

    }

    public String getUn() {
        return username;
    }

    public String getPw() {
        return password;
    }

    public String getTeam() {
        return team;
    }

    public Date getD() {
        return c.getTime();
    }

    public boolean checkUser(String u, String p) {

        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/s38f1", "root", "password");
            //here s38f1 is database name, root is username and password=+ 
            String q = "select * from account where username=? and password=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, u);
            pst.setString(2, p);
            pst.execute();
            rs = pst.getResultSet();
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(String u, String p, String t) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/s38f1", "root", "password") //here s38f1 is database name, root is root and password=-
                    ) {
                String query = "Insert INTO account(username, password,favorite_team) VALUES (?,?,?);";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, u);
                preparedStmt.setString(2, p);
                preparedStmt.setString(3, t);
                preparedStmt.execute();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkSchool(String t) {
        if (t == null) {
            return true;
        } else {
            for (Schools s : a.getAllSchools()) {
                if (t.equalsIgnoreCase(s.getName()) || t.equalsIgnoreCase(s.getAbbrev())) {
                    return true;
                }
            }
        }
        return false;
    }

}
