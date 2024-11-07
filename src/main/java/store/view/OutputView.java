package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.Product;

public class OutputView {

    private static final String HELLO_STATEMENT = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_PRODUCTS_STATEMENT = "현재 보유하고 있는 상품입니다.";
    private static final String NUMBER_FORMAT = "#,###";
    private static final String PRODUCT_FORMAT = "- %s %s원 ";
    private static final String QUANTITY_FORMAT = "%s개 ";
    private static final String OUT_OF_STOCK = "재고 없음";
    private static final String BLANK = "";
    private static final String NEW_LINE = System.lineSeparator();

    public void printHello() {
        System.out.println(HELLO_STATEMENT);
    }

    public void printCurrentProducts(List<Product> products) {
        System.out.println(CURRENT_PRODUCTS_STATEMENT + NEW_LINE);
        printEachProduct(products);
    }

    private void printEachProduct(List<Product> products) {
        products.stream()
                .map(this::formatProduct)
                .forEach(System.out::println);
    }

    private String formatProduct(Product product) {
        return String.format(PRODUCT_FORMAT, product.getName(), formatNumber(product.getPrice()))
                + formatQuantity(product)
                + formatPromotion(product);
    }

    private String formatNumber(int number) {
        DecimalFormat decimalFormat = new DecimalFormat(NUMBER_FORMAT);
        return decimalFormat.format(number);
    }

    private String formatQuantity(Product product) {
        if (product.getQuantity() > 0) {
            return String.format(QUANTITY_FORMAT, formatNumber(product.getQuantity()));
        }

        return OUT_OF_STOCK;
    }

    private String formatPromotion(Product product) {
        if (product.getPromotion() != null) {
            return product.getPromotion().getName();
        }

        return BLANK;
    }
}