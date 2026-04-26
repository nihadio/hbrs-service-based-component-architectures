package org.hbrs.seka.uebung1.test;

import org.hbrs.seka.uebung1.Caching;
import org.hbrs.seka.uebung1.DatabaseConnection;
import org.hbrs.seka.uebung1.ProductManagement;
import org.hbrs.seka.uebung1.HashMapCaching;
import org.hbrs.seka.uebung1.entities.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {

	private Connection connection;

	@BeforeEach
	public void setup() {
		try {
			connection = DatabaseConnection.getConnection();

			String sql = "CREATE TABLE IF NOT EXISTS products ("
					+ "id INT PRIMARY KEY AUTO_INCREMENT, "
					+ "name VARCHAR(255) NOT NULL, "
					+ "price DOUBLE NOT NULL)";

			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	public void cleanup() {
		try {
			connection.createStatement().execute("DROP TABLE IF EXISTS products");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Originaler Round-Trip-Test direkt via JDBC
	@Test
	public void roundTrip() {
		Product productTarget = insertProduct();

		List<Product> products = readProducts();
		Product productActual = products.get(0);

		assertEquals(productTarget, productActual);
	}

	// Round-Trip-Test via Komponente (Port)
	@Test
	public void roundTripViaComponent() {
		ProductManagement pm = new ProductManagement();
		pm.setCaching(new HashMapCaching<>());
		pm.openSession();

		Product expected = new Product(0, "Motor 2.0", 250.0);
		pm.saveProduct(expected);

		List<Product> results = pm.getProductByName("Motor");
		assertEquals(1, results.size());
		assertEquals(expected, results.get(0));

		pm.closeSession();
	}

	// Prüft, ob das Caching funktioniert: zweiter Aufruf kommt aus dem Cache
	@Test
	public void cacheIsUsedOnSecondCall() {
		ProductManagement pm = new ProductManagement();
		Caching<Product> cache = new HashMapCaching<>();
		pm.setCaching(cache);
		pm.openSession();

		pm.saveProduct(new Product(0, "Getriebe", 500.0));

		pm.getProductByName("Getriebe"); // füllt den Cache
		assertTrue(cache.isCached("Getriebe"));

		List<Product> cachedResult = pm.getProductByName("Getriebe"); // aus dem Cache
		assertEquals(1, cachedResult.size());
		assertEquals("Getriebe", cachedResult.get(0).getName());

		pm.closeSession();
	}

	private List<Product> readProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products";

		try {
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				products.add(new Product(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getDouble("price")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	private Product insertProduct() {
		String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
		Product productTarget = new Product(1, "My Motor 1.0", 100.0);

		try {
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			pstmt.setString(1, productTarget.getName());
			pstmt.setDouble(2, productTarget.getPrice());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productTarget;
	}
}
