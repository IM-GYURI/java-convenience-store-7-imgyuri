package store.service;

import java.util.List;
import store.domain.Product;
import store.util.ProductLoader;

public class StoreService {

    private final ProductLoader productLoader;

    public StoreService(ProductLoader productLoader) {
        this.productLoader = productLoader;
    }

    public List<Product> loadProductsFromFile(String productFilePath) {
        return productLoader.loadProducts(productFilePath);
    }
}
