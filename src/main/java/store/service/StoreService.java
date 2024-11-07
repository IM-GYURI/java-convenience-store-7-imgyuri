package store.service;

import store.domain.Products;
import store.util.ProductLoader;

public class StoreService {

    private final ProductLoader productLoader;

    public StoreService(ProductLoader productLoader) {
        this.productLoader = productLoader;
    }

    public Products loadProductsFromFile(String productFilePath) {
        return productLoader.loadProducts(productFilePath);
    }
}
