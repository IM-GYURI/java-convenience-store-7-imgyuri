package store.domain;

import java.util.List;

public record Products(
        List<Product> products) {

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
