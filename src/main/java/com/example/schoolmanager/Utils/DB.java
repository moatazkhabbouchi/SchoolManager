package com.example.schoolmanager.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static DB instance;
    final String URL = "jdbc:mysql://127.0.0.1:3306/emploidutemps";
    final String USERNAME = "root";
    final String PWD = "";
    private Connection cnx;

    private DB(){
        try{
            cnx = DriverManager.getConnection(URL, USERNAME, PWD);
            System.out.println("Connected...");
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Connection getCnx(){return this.cnx;}

    public static DB getInstance(){
        if(instance == null){
            instance = new DB();
        }
        return instance;
    }
}
