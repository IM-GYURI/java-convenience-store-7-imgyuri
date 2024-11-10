package store.util;

import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.ParsedItem;
import store.exception.ErrorMessage;

public class PurchaseItemFactory {

    public static PurchaseItem createPurchaseItem(ParsedItem parsedItem, Products products) {
        Product product = findMatchProduct(parsedItem.name(), products);
        return new PurchaseItem(product, parsedItem.quantity());
    }

    private static Product findMatchProduct(String name, Products products) {
        return products.getProducts().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXISTS.name()));
    }
}
