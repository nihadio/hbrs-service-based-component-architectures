package org.hbrs.seka.uebung1;

import org.hbrs.seka.uebung1.entities.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductController {

	private final ProductRepository repository = new ProductRepository();

	private Caching<Product> cache = new NoOpCaching<>();
	private Connection connection;
	private SessionState state = SessionState.NEW;

	public void setCaching(Caching<Product> cache) {
		this.cache = cache;
	}

	public void openSession() {
		if (state == SessionState.OPEN) {
			throw new IllegalStateException("Session is already open.");
		}

		try {
			connection = DatabaseConnection.getConnection();
			state = SessionState.OPEN;
		} catch (SQLException e) {
			throw new RuntimeException("Could not open database session.", e);
		}
	}

	public void closeSession() {
		ensureSessionIsOpen();

		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Could not close database session.", e);
		} finally {
			connection = null;
			state = SessionState.CLOSED;
		}
	}

	public List<Product> getProductByName(String name) {
		ensureSessionIsOpen();

		if (name == null || name.isBlank()) {
			return List.of();
		}

		if (cache.isCached(name)) {
			return cache.getCachedResult(name);
		}

		List<Product> products = repository.findByName(name, connection);
		cache.cacheResult(name, products);

		return products;
	}

	public void saveProduct(Product product) {
		ensureSessionIsOpen();
		repository.save(product, connection);
	}

	private void ensureSessionIsOpen() {
		if (state != SessionState.OPEN || connection == null) {
			throw new IllegalStateException(
					"No open session. Call openSession() before invoking business methods.");
		}
	}

	private enum SessionState {
		NEW,
		OPEN,
		CLOSED
	}
}
