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
        System.out.println(errorMessage + Statement.NEW_LINE.message);
    }

    private void printEachProduct(List<Product> products) {
        products.forEach(this::printFormattedProduct);
        System.out.println();
    }

    private void printFormattedProduct(Product product) {
        if (product.getPromotion() != null) {
            System.out.println(
                    formatProduct(product, product.getStock().getPromotionStock(), product.getPromotion().getName()));
            System.out.println(formatProduct(product, product.getStock().getRegularStock(), Statement.BLANK.message));
            return;
        }

        System.out.println(formatProduct(product, product.getStock().getRegularStock(), Statement.BLANK.message));
    }

    private String formatProduct(Product product, int stock, String promotion) {
        return String.format(Statement.PRODUCT_FORMAT.message
                , product.getName(), formatNumber(product.getPrice()))
                + formatQuantity(stock)
                + formatPromotion(promotion);
    }

    private String formatNumber(int number) {
        DecimalFormat decimalFormat = new DecimalFormat(Statement.NUMBER_FORMAT.message);
        return decimalFormat.format(number);
    }

    private String formatQuantity(int quantity) {
        if (quantity > 0) {
            return String.format(Statement.QUANTITY_FORMAT.message, formatNumber(quantity));
        }

        return Statement.OUT_OF_STOCK.message;
    }

    private String formatPromotion(String promotionName) {
        if (!promotionName.equals(Statement.BLANK.message)) {
            return promotionName;
        }

        return Statement.BLANK.message;
    }
}