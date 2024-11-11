package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private List<Product> products;

    public Products(
            List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products.stream().toList();
    }
}
