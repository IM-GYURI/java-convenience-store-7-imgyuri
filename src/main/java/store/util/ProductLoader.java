package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

public class ProductLoader {

    private final static String SPLITTER = ",";

    private final Map<String, Promotion> promotions;
    private final List<Product> products;

    public ProductLoader(Map<String, Promotion> promotions) {
        this.promotions = promotions;
        this.products = new ArrayList<>();
    }

    public Products loadProducts(String filePath) {
        Optional<Product> lastProduct = Optional.empty();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            for (String line : (Iterable<String>) br.lines()::iterator) {
                lastProduct = Optional.of(processLine(line, lastProduct.orElse(null)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Products(products.stream().toList());
    }

    private Product processLine(String line, Product lastProduct) {
        Product product = createProduct(line);

        if (lastProduct != null) {
            if (lastProduct.getPromotion() != null
                    && !product.getName().equals(lastProduct.getName())) {
                addNoPromotionProduct(lastProduct);
            }
        }
        products.add(product);

        return product;
    }

    private Product createProduct(String line) {
        String[] parts = line.split(SPLITTER);

        String name = parts[0];
        int price = parseNumber(parts[1]);
        int quantity = parseNumber(parts[2]);
        Promotion promotion = parsePromotion(parts[3]);

        return new Product(name, price, quantity, promotion);
    }

    private void addNoPromotionProduct(Product lastProduct) {
        products.add(
                new Product(
                        lastProduct.getName(),
                        lastProduct.getPrice(),
                        0,
                        null
                )
        );
    }

    private int parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    private Promotion parsePromotion(String promotionName) {
        return promotions.get(promotionName);
    }
}
