package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.dto.GivenItemDto;
import store.dto.PurchasedDto;
import store.dto.ReceiptDto;

public class OutputView {

    private static final int MINUS_FOR_DISCOUNT = -1;

    public static void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage + Statement.NEW_LINE.message);
    }

    public void printHello() {
        System.out.println(Statement.HELLO_STATEMENT.message);
    }

    public void printCurrentProducts(Products products) {
        System.out.println(Statement.CURRENT_PRODUCTS_STATEMENT.message + Statement.NEW_LINE.message);
        printEachProduct(products.products());
    }

    public void printReceipt(ReceiptDto receiptDto) {
        System.out.println(Statement.START_OF_RECEIPT.message);
        System.out.println(Statement.HEADER_OF_PURCHASE.message);
        printPurchases(receiptDto.purchasedItems());

        System.out.println(Statement.HEADER_OF_GIVEN.message);
        printGivenItems(receiptDto.givenItems());
        System.out.println(Statement.DIVIDE_LINE.message);

        printFooter(receiptDto);
    }

    private void printEachProduct(List<Product> products) {
        products.forEach(this::printFormattedProduct);
        System.out.println();
    }

    private void printFormattedProduct(Product product) {
        if (product.promotion() != null) {
            System.out.println(formatProduct(product
                    , product.stock().getPromotionStock(), product.promotion().getName()));
            System.out.println(formatProduct(product, product.stock().getRegularStock(), Statement.BLANK.message));
            return;
        }

        System.out.println(formatProduct(product, product.stock().getRegularStock(), Statement.BLANK.message));
    }

    private void printPurchases(List<PurchasedDto> purchasedItems) {
        purchasedItems.forEach(this::printEachPurchase);
    }

    private void printEachPurchase(PurchasedDto purchasedDto) {
        System.out.printf(Statement.PURCHASE_FORMAT.message + Statement.NEW_LINE.message
                , purchasedDto.name(), formatNumber(purchasedDto.quantity())
                , formatNumber(purchasedDto.totalPrice()));
    }

    private void printGivenItems(List<GivenItemDto> givenItems) {
        givenItems.forEach(this::printEachGivenItem);
    }

    private void printEachGivenItem(GivenItemDto givenItemDto) {
        if (givenItemDto.freeQuantity() != 0) {
            System.out.printf(Statement.GIVEN_FORMAT.message + Statement.NEW_LINE.message
                    , givenItemDto.name(), formatNumber(givenItemDto.freeQuantity()));
        }
    }

    private void printFooter(ReceiptDto receiptDto) {
        System.out.printf(Statement.TOTAL_PRICE_FORMAT.message + Statement.NEW_LINE.message,
                receiptDto.totalQuantity(), formatNumber(receiptDto.totalPrice()));
        System.out.printf(Statement.PROMOTION_DISCOUNT_FORMAT.message + Statement.NEW_LINE.message
                , formatNumber(MINUS_FOR_DISCOUNT * receiptDto.promotionDiscountPrice()));
        System.out.printf(Statement.MEMBERSHIP_DISCOUNT_FORMAT.message + Statement.NEW_LINE.message
                , formatNumber(MINUS_FOR_DISCOUNT * receiptDto.membershipDiscountPrice()));
        System.out.printf(Statement.PAY_PRICE_FORMAT.message + Statement.NEW_LINE.message
                , formatNumber(receiptDto.payPrice()));
    }

    private String formatProduct(Product product, int stock, String promotion) {
        return String.format(Statement.PRODUCT_FORMAT.message
                , product.name(), formatNumber(product.price()))
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