package com.Jakatakz.jda_bot_bd;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect
{
	private static Connection connection = null;
	
	public static Connection dbConnect()
	{
		try
		{
			Connection connection =
					  DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott",
					  "tiger"); 
	
			return connection;
		}
		catch (Exception ex)
		{
			return null;
		}
	
	}
}
