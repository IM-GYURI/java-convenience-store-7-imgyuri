package store.domain;

public record Product(
        String name,
        int price,
        Stock stock,
        Promotion promotion) {

    public int calculateTotalPrice(int quantity) {
        return price * quantity;
    }

    public void updateStock(int purchasedQuantity) {
        int availablePromotionStock = stock.getPromotionStock();
        int promotionQuantity = Math.min(availablePromotionStock, purchasedQuantity);
        int regularQuantity = purchasedQuantity - promotionQuantity;

        stock.updatePromotionStock(promotionQuantity);
        stock.updateRegularStock(regularQuantity);
    }

    public int calculatePromotionDiscount(int purchasedQuantity) {
        if (promotion == null || !promotion.isWithinPromotionPeriod()) {
            return 0;
        }

        int eligibleForPromotion = promotion.calculateFreeQuantity(purchasedQuantity, stock.getPromotionStock());
        return eligibleForPromotion * price;
    }

    public int getPromotionStock() {
        return stock.getPromotionStock();
    }

    public int getRegularStock() {
        return stock.getRegularStock();
    }

    public int getFullStock() {
        return getPromotionStock() + getRegularStock();
    }
}
