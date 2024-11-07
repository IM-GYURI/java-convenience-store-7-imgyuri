package store.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.exception.ErrorMessage;
import store.exception.ValidatorBuilder;

public class PurchaseItemParser {

    private static final String ITEM_SPLITTER = ",";
    private static final String DETAIL_SPLITTER = "-";
    private static final String PURCHASE_ITEM_REGEX = "^\\[[^\\[]+?-\\d+]\\s*(,\\s*\\[[^\\[]+?-\\d+])*$";

    private final List<PurchaseItem> purchaseItems;
    private final Set<String> itemNames;

    public PurchaseItemParser() {
        this.purchaseItems = new ArrayList<>();
        this.itemNames = new HashSet<>();
    }

    public List<PurchaseItem> parsePurchaseItems(String inputStatement, Products products) {
        validateInput(inputStatement);

        String[] purchaseParts = inputStatement.split(ITEM_SPLITTER);
        processPurchaseParts(purchaseParts, products);

        return purchaseItems;
    }

    private void processPurchaseParts(String[] purchaseParts, Products products) {
        for (String part : purchaseParts) {
            String[] itemDetails = part.split(DETAIL_SPLITTER);
            String name = parseEachInput(itemDetails[0]);
            int quantity = validateQuantity(parseEachInput(itemDetails[1]));

            validateItemName(name, products);
            itemNames.add(name);

            purchaseItems.add(new PurchaseItem(name, quantity));
        }
    }

    private String parseEachInput(String namePart) {
        return namePart.replace("[", "").replace("]", "").trim();
    }

    private void validateInput(String inputStatement) {
        ValidatorBuilder.from(inputStatement)
                .validate(input -> input == null || input.isBlank(), ErrorMessage.INPUT_NOT_EMPTY)
                .validate(input -> !input.matches(PURCHASE_ITEM_REGEX), ErrorMessage.INVALID_PURCHASE_FORMAT)
                .get();
    }

    private void validateItemName(String name, Products products) {
        ValidatorBuilder.from(name)
                .validate(itemNames::contains, ErrorMessage.PURCHASE_ITEM_DUPLICATED)
                .validate(n -> !products.existsProduct(name), ErrorMessage.PRODUCT_NOT_EXISTS);
    }

    private int validateQuantity(String quantityInput) {
        return ValidatorBuilder.from(quantityInput)
                .validateIsInteger()
                .validateInteger(value -> value <= 0, ErrorMessage.INVALID_QUANTITY)
                .getNumericValue();
    }
}
