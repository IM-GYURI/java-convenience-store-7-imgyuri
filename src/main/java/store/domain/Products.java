package store.domain;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public boolean existsProduct(String productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(productName));
    }

    public List<Product> getProducts() {
        return products;
    }
}
