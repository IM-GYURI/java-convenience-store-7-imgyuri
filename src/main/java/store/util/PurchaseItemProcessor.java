package store.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.ParsedItem;
import store.validation.PurchaseInputValidator;

public class PurchaseItemProcessor {

    private final List<PurchaseItem> purchaseItems;
    private final Set<String> itemNames;

    public PurchaseItemProcessor() {
        this.purchaseItems = new ArrayList<>();
        this.itemNames = new HashSet<>();
    }

    public List<PurchaseItem> parsePurchaseItems(String inputStatement, Products products) {
        clearStoredItems();
        PurchaseInputValidator.validateInputFormat(inputStatement);

        List<String> inputParts = PurchaseItemParser.splitItems(inputStatement);
        processItems(inputParts, products);

        return purchaseItems;
    }

    private void clearStoredItems() {
        purchaseItems.clear();
        itemNames.clear();
    }

    private void processItems(List<String> inputParts, Products products) {
        for (String part : inputParts) {
            ParsedItem parsedItem = PurchaseItemParser.parseItem(part);
            PurchaseInputValidator.validateStockAndDuplication(parsedItem, itemNames, products);
            PurchaseItem purchaseItem = PurchaseItemFactory.createPurchaseItem(parsedItem, products);
            addItemToPurchase(purchaseItem);
        }
    }

    private void addItemToPurchase(PurchaseItem purchaseItem) {
        itemNames.add(purchaseItem.getProduct().getName());
        purchaseItems.add(purchaseItem);
    }
}
