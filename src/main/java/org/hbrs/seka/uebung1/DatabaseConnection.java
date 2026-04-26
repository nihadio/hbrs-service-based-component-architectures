package org.hbrs.seka.uebung1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:h2:~/test";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static Connection getConnection() throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		initializeSchema(connection);
		return connection;
	}

	private static void initializeSchema(Connection connection) throws SQLException {
		String sql = """
				CREATE TABLE IF NOT EXISTS products (
				    id INT AUTO_INCREMENT PRIMARY KEY,
				    name VARCHAR(255) NOT NULL,
				    price DOUBLE NOT NULL
				)
				""";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		}
	}
}
