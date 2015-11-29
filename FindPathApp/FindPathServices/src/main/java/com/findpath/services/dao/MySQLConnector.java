package com.findpath.services.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Getting MySQL Connection.
 * 
 * @author Максим
 */
public class MySQLConnector
{
    private static String theirUser = "root";
    private static String theirPassword = "";
    private static String theirUrl = "jdbc:mysql://localhost:3306/find_path";
    private static Connection theirConnection;

    MySQLConnector()
    {
        
    }
    
    /**
     * Provide connection to DB.
     * 
     * @return connection
     */
    public static Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            theirConnection = DriverManager.getConnection(theirUrl, theirUser, theirPassword);
        }
        catch (SQLException exc)
        {
            System.out.println("error while connecting to DB");
        }
        catch (ClassNotFoundException exc)
        {
            System.out.println("Class com.mysql.jdbc.Driver not found");
        }
        return theirConnection;
    }
}
