package store.util;

import store.domain.Product;
import store.domain.PurchaseItem;

public class PromotionCalculator {

    public static int calculatePromotionDiscount(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        int price = product.price();

        if (product.promotion() != null) {
            int eligibleForPromotion = product.promotion().calculateFreeQuantity(purchaseItem.getQuantity());
            return eligibleForPromotion * price;
        }

        return 0;
    }
}
