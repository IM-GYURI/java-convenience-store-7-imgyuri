package store.dto;

import store.domain.Product;
import store.domain.PurchaseItem;

public record GivenItemDto(
        String name,
        int freeQuantity,
        int price) {

    public static GivenItemDto from(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        int freeQuantity = calculateFreeQuantity(purchaseItem);

        return new GivenItemDto(product.name(), freeQuantity, product.price());
    }

    private static int calculateFreeQuantity(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();

        if (product.promotion() != null) {
            return product.promotion().calculateFreeQuantity(product.stock().getPromotionStock());
        }

        return 0;
    }
}
