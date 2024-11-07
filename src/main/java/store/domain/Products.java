package store.domain;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean existsProduct(String productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(productName));
    }
}
