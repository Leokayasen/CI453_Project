package com.bitwave.java_atmapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// port is 3306
public class db_connect {
    private static final String URL = "jdbc:mysql://localhost/kl699_javaATM_App_db?socket=/var/run/mysqld/mysqld.sock";
    private static final String USER = "kl699_javaApp_remote";
    private static final String PASSWORD = "qLRLPKst3Xa1";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try(Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Connected to database successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
