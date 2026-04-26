package org.hbrs.seka.uebung1;

import org.hbrs.seka.uebung1.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

	public void save(Product product, Connection connection) {
		String sql = "INSERT INTO products (name, price) VALUES (?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, product.getName());
			pstmt.setDouble(2, product.getPrice());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Could not save product.", e);
		}
	}

	public List<Product> findByName(String name, Connection connection) {
		String sql = """
				SELECT id, name, price
				FROM products
				WHERE LOWER(name) LIKE LOWER(?)
				ORDER BY id
				""";

		List<Product> products = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name.trim() + "%");

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					products.add(new Product(
							rs.getInt("id"),
							rs.getString("name"),
							rs.getDouble("price")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Could not query products by name.", e);
		}

		return products;
	}
}
