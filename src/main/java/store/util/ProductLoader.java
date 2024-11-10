package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Stock;

public class ProductLoader {

    private static final String SPLITTER = ",";
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int STOCK_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

    private final Map<String, Promotion> promotions;
    private final List<Product> products;

    public ProductLoader(Map<String, Promotion> promotions) {
        this.promotions = promotions;
        this.products = new ArrayList<>();
    }

    public Products loadProducts(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            br.lines().forEach(this::processLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Products(products.stream().toList());
    }

    private void processLine(String line) {
        String[] parts = line.split(SPLITTER);
        String name = parts[NAME_INDEX];
        int price = parseNumber(parts[PRICE_INDEX]);
        int stockQuantity = parseNumber(parts[STOCK_INDEX]);
        Promotion promotion = parsePromotion(parts[PROMOTION_INDEX]);

        updateOrAddProduct(name, price, stockQuantity, promotion);
    }

    private void updateOrAddProduct(String name, int price, int stockQuantity, Promotion promotion) {
        Product existingProduct = findProductByName(name);

        if (existingProduct == null) {
            addNewProduct(name, price, stockQuantity, promotion);
            return;
        }

        updateStock(existingProduct, stockQuantity, promotion);
    }

    private Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void addNewProduct(String name, Integer price, Integer stockQuantity, Promotion promotion) {
        if (promotion != null) {
            Stock stock = new Stock(stockQuantity, 0);
            products.add(new Product(name, price, stock, promotion));
            return;
        }

        Stock stock = new Stock(0, stockQuantity);
        products.add(new Product(name, price, stock, promotion));
    }

    private void updateStock(Product product, int stockQuantity, Promotion promotion) {
        Stock currentStock = product.stock();

        if (promotion != null) {
            currentStock.setPromotionStock(stockQuantity);
            return;
        }

        currentStock.setRegularStock(stockQuantity);
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
