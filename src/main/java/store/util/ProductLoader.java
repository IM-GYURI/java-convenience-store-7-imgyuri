package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;

public class ProductLoader {

    private final static String SPLITTER = ",";

    private final Map<String, Promotion> promotions;

    public ProductLoader(Map<String, Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Product> loadProducts(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();

            return br.lines()
                    .map(this::createProduct)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Product createProduct(String line) {
        String[] parts = line.split(SPLITTER);

        String name = parts[0];
        int price = parseNumber(parts[1]);
        int quantity = parseNumber(parts[2]);
        Promotion promotion = parsePromotion(parts[3]);

        return new Product(name, price, quantity, promotion);
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
