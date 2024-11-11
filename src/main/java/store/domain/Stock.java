package store.domain;

public class Stock {

    private int promotionStock;
    private int regularStock;

    public Stock(int promotionStock, int regularStock) {
        this.promotionStock = promotionStock;
        this.regularStock = regularStock;
    }

    public void minusPromotionStock(int quantity) {
        promotionStock -= quantity;
    }

    public void minusRegularStock(int quantity) {
        regularStock -= quantity;
    }

    public int getRegularStock() {
        return regularStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public void setRegularStock(int regularStock) {
        this.regularStock = regularStock;
    }

    public void setPromotionStock(int promotionStock) {
        this.promotionStock = promotionStock;
    }
}
