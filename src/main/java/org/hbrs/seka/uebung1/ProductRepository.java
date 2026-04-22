package org.hbrs.seka.uebung1;

import org.hbrs.seka.uebung1.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // Weitere CRUD-Methoden können hinzugefügt werden
    public void save(Product product) {
        String sql1 = "INSERT INTO products (name, price) VALUES (?, ?)";

        try {
            PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql1);
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.executeUpdate();
            System.out.println("Product inserted successfully.");
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try {
            PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }


}
