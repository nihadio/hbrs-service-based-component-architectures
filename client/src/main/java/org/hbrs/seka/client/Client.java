package org.hbrs.seka.client;

import org.hbrs.seka.uebung1.HashMapCaching;
import org.hbrs.seka.uebung1.ProductManagement;
import org.hbrs.seka.uebung1.entities.Product;

import java.util.List;

public class Client {

	public static void main(String[] args) {
		ProductManagement pm = new ProductManagement();
		pm.setCaching(new HashMapCaching<>());
		pm.openSession();

		pm.saveProduct(new Product(0, "Motor 1.0", 100.0));
		pm.saveProduct(new Product(0, "Motor 2.0", 250.0));

		List<Product> results = pm.getProductByName("Motor");
		results.forEach(System.out::println);

		pm.closeSession();
	}
}
