package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class TestCase1 {
	@Test
	public void findByName(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cloud_note",
					"root", "root");
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			System.out.println("ณ๖ดํมห");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
