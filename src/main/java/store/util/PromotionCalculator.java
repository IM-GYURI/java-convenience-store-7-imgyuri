package store.util;

import store.domain.Product;
import store.domain.PurchaseItem;

public class PromotionCalculator {

    public static int calculatePromotionDiscount(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();

        int quantity = purchaseItem.getQuantity();
        int price = product.getPrice();

        if (product.getPromotion() != null) {
            int eligibleForPromotion = product.getPromotion().calculateFreeQuantity(quantity);
            return eligibleForPromotion * price;
        }

        return 0;
    }
}
