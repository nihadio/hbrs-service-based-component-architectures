package org.hbrs.seka.uebung1;

import org.hbrs.seka.uebung1.entities.Product;

import java.util.List;

public class ProductManagement implements ProductManagementInt {

	private final Logging logger = new Logger();
	private final ProductController controller = new ProductController();

	public void setCaching(Caching<Product> caching) {
		controller.setCaching(caching);
	}

	@Override
	public void openSession() {
		controller.openSession();
	}

	@Override
	public void closeSession() {
		controller.closeSession();
	}

	@Override
	public List<Product> getProductByName(String name) {
		logger.log("getProductByName", "Suchwort: " + name);
		return controller.getProductByName(name);
	}

	@Override
	public void saveProduct(Product product) {
		logger.log("saveProduct", "Produkt: " + product.getName());
		controller.saveProduct(product);
	}
}
