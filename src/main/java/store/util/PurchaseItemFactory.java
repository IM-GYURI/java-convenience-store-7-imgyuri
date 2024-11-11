package store.util;

import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.ParsedItemDto;
import store.exception.ErrorMessage;

public class PurchaseItemFactory {

    public static PurchaseItem createPurchaseItem(ParsedItemDto parsedItemDto, Products products) {
        Product product = findMatchProduct(parsedItemDto.name(), products);
        return new PurchaseItem(product, parsedItemDto.quantity());
    }

    private static Product findMatchProduct(String name, Products products) {
        return products.getProducts().stream()
                .filter(product -> product.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXISTS.name()));
    }
}
