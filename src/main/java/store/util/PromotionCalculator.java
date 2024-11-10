package store.util;

import store.domain.Product;
import store.domain.PurchaseItem;

public class PromotionCalculator {

    public static int calculatePromotionDiscount(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        int price = product.price();

        if (product.promotion() != null && product.promotion().isWithinPromotionPeriod()) {
            int eligibleForPromotion = product.promotion()
                    .calculateFreeQuantity(purchaseItem.getQuantity(), product.stock().getPromotionStock());
            return eligibleForPromotion * price;
        }

        return 0;
    }
}
