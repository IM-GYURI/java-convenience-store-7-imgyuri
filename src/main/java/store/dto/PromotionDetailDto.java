package store.dto;

import store.domain.Product;
import store.domain.PurchaseItem;

public record PromotionDetailDto(
        int promotionQuantity,
        int remainingQuantity) {

    public static PromotionDetailDto from(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();

        if (product.promotion() != null) {
            return product.promotion()
                    .calculatePromotionAndFree(purchaseItem.getQuantity(), product.stock().getPromotionStock());
        }

        return new PromotionDetailDto(0, product.stock().getRegularStock());
    }
}
