package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.Product;
import store.domain.Products;

public class OutputView {

    public void printHello() {
        System.out.println(Statement.HELLO_STATEMENT.message);
    }

    public void printCurrentProducts(Products products) {
        System.out.println(Statement.CURRENT_PRODUCTS_STATEMENT.message + Statement.NEW_LINE.message);
        printEachProduct(products.getProducts());
    }

    public static void printErrorMessage(String errorMessage) {
        System.out.println(Statement.NEW_LINE.message + errorMessage);
    }

    private void printEachProduct(List<Product> products) {
        products.stream()
                .map(this::formatProduct)
                .forEach(System.out::println);
        System.out.println();
    }

    private String formatProduct(Product product) {
        return String.format(Statement.PRODUCT_FORMAT.message, product.getName(), formatNumber(product.getPrice()))
                + formatQuantity(product)
                + formatPromotion(product);
    }

    private String formatNumber(int number) {
        DecimalFormat decimalFormat = new DecimalFormat(Statement.NUMBER_FORMAT.message);
        return decimalFormat.format(number);
    }

    private String formatQuantity(Product product) {
        if (product.getQuantity() > 0) {
            return String.format(Statement.QUANTITY_FORMAT.message, formatNumber(product.getQuantity()));
        }

        return Statement.OUT_OF_STOCK.message;
    }

    private String formatPromotion(Product product) {
        if (product.getPromotion() != null) {
            return product.getPromotion().getName();
        }

        return Statement.BLANK.message;
    }
}