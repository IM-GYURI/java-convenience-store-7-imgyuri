package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.dto.GivenItemDto;
import store.dto.PurchasedDto;
import store.dto.ReceiptDto;

public class OutputView {

    public static void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage + Sentence.NEW_LINE.message);
    }

    public void printBlank() {
        System.out.println();
    }

    public void printHello() {
        System.out.println(Sentence.HELLO_STATEMENT.message);
    }

    public void printCurrentProducts(Products products) {
        System.out.println(Sentence.CURRENT_PRODUCTS_STATEMENT.message + Sentence.NEW_LINE.message);
        printEachProduct(products.products());
    }

    public void printReceipt(ReceiptDto receiptDto) {
        System.out.println(Sentence.NEW_LINE.message + Sentence.START_OF_RECEIPT.message);
        System.out.println(Sentence.HEADER_OF_PURCHASE.message);
        printPurchases(receiptDto.purchasedItems());

        System.out.println(Sentence.HEADER_OF_GIVEN.message);
        printGivenItems(receiptDto.givenItems());
        System.out.println(Sentence.DIVIDE_LINE.message);

        printFooter(receiptDto);
    }

    private void printEachProduct(List<Product> products) {
        products.forEach(this::printFormattedProduct);
        System.out.println();
    }

    private void printFormattedProduct(Product product) {
        if (product.promotion() != null) {
            System.out.println(formatProduct(product
                    , product.getPromotionStock(), product.promotion().getName()));
            System.out.println(formatProduct(product, product.getRegularStock(), Sentence.BLANK.message));
            return;
        }

        System.out.println(formatProduct(product, product.getRegularStock(), Sentence.BLANK.message));
    }

    private void printPurchases(List<PurchasedDto> purchasedItems) {
        purchasedItems.forEach(this::printEachPurchase);
    }

    private void printEachPurchase(PurchasedDto purchasedDto) {
        System.out.printf(Sentence.PURCHASE_FORMAT.message + Sentence.NEW_LINE.message
                , purchasedDto.name(), formatNumber(purchasedDto.quantity())
                , formatNumber(purchasedDto.totalPrice()));
    }

    private void printGivenItems(List<GivenItemDto> givenItems) {
        givenItems.forEach(this::printEachGivenItem);
    }

    private void printEachGivenItem(GivenItemDto givenItemDto) {
        if (givenItemDto.freeQuantity() != 0) {
            System.out.printf(Sentence.GIVEN_FORMAT.message + Sentence.NEW_LINE.message
                    , givenItemDto.name(), formatNumber(givenItemDto.freeQuantity()));
        }
    }

    private void printFooter(ReceiptDto receiptDto) {
        System.out.printf(Sentence.TOTAL_PRICE_FORMAT.message + Sentence.NEW_LINE.message,
                receiptDto.totalQuantity(), formatNumber(receiptDto.totalPrice()));
        System.out.printf(Sentence.PROMOTION_DISCOUNT_FORMAT.message + Sentence.NEW_LINE.message
                , formatNumber(receiptDto.promotionDiscountPrice()));
        System.out.printf(Sentence.MEMBERSHIP_DISCOUNT_FORMAT.message + Sentence.NEW_LINE.message
                , formatNumber(receiptDto.membershipDiscountPrice()));
        System.out.printf(Sentence.PAY_PRICE_FORMAT.message + Sentence.NEW_LINE.message
                , formatNumber(receiptDto.payPrice()));
    }

    private String formatProduct(Product product, int stock, String promotion) {
        return String.format(Sentence.PRODUCT_FORMAT.message
                , product.name(), formatNumber(product.price()))
                + formatQuantity(stock)
                + formatPromotion(promotion);
    }

    private String formatNumber(int number) {
        DecimalFormat decimalFormat = new DecimalFormat(Sentence.NUMBER_FORMAT.message);
        return decimalFormat.format(number);
    }

    private String formatQuantity(int quantity) {
        if (quantity > 0) {
            return String.format(Sentence.QUANTITY_FORMAT.message, formatNumber(quantity));
        }

        return Sentence.OUT_OF_STOCK.message;
    }

    private String formatPromotion(String promotionName) {
        if (!promotionName.equals(Sentence.BLANK.message)) {
            return promotionName;
        }

        return Sentence.BLANK.message;
    }
}