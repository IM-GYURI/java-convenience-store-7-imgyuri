package store.domain;

public class PurchaseItem {

    private final Product product;
    private int quantity;

    public PurchaseItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public boolean needsAdditionalPurchase() {
        Promotion promotion = product.promotion();

        if (promotion != null && promotion.isWithinPromotionPeriod()) {
            int additionalPurchase = promotion.calculateAdditionalPurchase(quantity);

            return additionalPurchase > 0
                    && quantity + additionalPurchase <= product.stock().getPromotionStock();
        }

        return false;
    }

    public void buyMore() {
        this.quantity += product.promotion().calculateAdditionalPurchase(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
