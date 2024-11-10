package store.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.exception.ErrorMessage;
import store.exception.ValidatorBuilder;

public class PurchaseItemParser {

    private static final String ITEM_SPLITTER = ",";
    private static final String DETAIL_SPLITTER = "-";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String REPLACE_BLANK = "";
    private static final int QUANTITY_STANDARD = 1;
    private static final int NAME_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;
    private static final String PURCHASE_ITEM_REGEX = "^\\[[^\\[]+?-\\d+]\\s*(,\\s*\\[[^\\[]+?-\\d+])*$";

    private final List<PurchaseItem> purchaseItems;
    private final Set<String> itemNames;

    public PurchaseItemParser() {
        this.purchaseItems = new ArrayList<>();
        this.itemNames = new HashSet<>();
    }

    public List<PurchaseItem> parsePurchaseItems(String inputStatement, Products products) {
        clearStoredItems();
        validateInput(inputStatement);

        String[] purchaseParts = inputStatement.split(ITEM_SPLITTER);
        processPurchaseParts(purchaseParts, products);

        return purchaseItems;
    }

    private void clearStoredItems() {
        purchaseItems.clear();
        itemNames.clear();
    }

    private void processPurchaseParts(String[] purchaseParts, Products products) {
        for (String part : purchaseParts) {
            String[] itemDetails = part.split(DETAIL_SPLITTER);
            String name = parseEachInput(itemDetails[NAME_INDEX]);
            int quantity = validateQuantity(parseEachInput(itemDetails[QUANTITY_INDEX]));

            validateItemName(name, products);
            itemNames.add(name);

            purchaseItems.add(new PurchaseItem(findMatchProduct(name, products), quantity));
        }
    }

    private String parseEachInput(String namePart) {
        return namePart.replace(LEFT_BRACKET, REPLACE_BLANK).replace(RIGHT_BRACKET, REPLACE_BLANK).trim();
    }

    private void validateInput(String inputStatement) {
        ValidatorBuilder.from(inputStatement)
                .validate(input -> input == null || input.isBlank(), ErrorMessage.INPUT_NOT_EMPTY)
                .validate(input -> !input.matches(PURCHASE_ITEM_REGEX), ErrorMessage.INVALID_PURCHASE_FORMAT)
                .get();
    }

    private Product findMatchProduct(String name, Products products) {
        return products.getProducts().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXISTS.name()));
    }

    private void validateItemName(String name, Products products) {
        ValidatorBuilder.from(name)
                .validate(itemNames::contains, ErrorMessage.PURCHASE_ITEM_DUPLICATED)
                .validate(n -> !products.existsProduct(name), ErrorMessage.PRODUCT_NOT_EXISTS);
    }

    private int validateQuantity(String quantityInput) {
        return ValidatorBuilder.from(quantityInput)
                .validateIsInteger()
                .validateInteger(value -> value < QUANTITY_STANDARD, ErrorMessage.INVALID_QUANTITY)
                .getNumericValue();
    }
}
