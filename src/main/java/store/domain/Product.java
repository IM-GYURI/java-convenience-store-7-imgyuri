package store.domain;

public class Product {

    private String name;
    private int price;
    private Stock stock;
    private Promotion promotion;

    public Product(String name, int price, Stock stock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public int calculateRegularQuantity(int requestedQuantity) {
        int promotionStock = stock.getPromotionStock();
        int availableForPromotion = Math.min(promotionStock, requestedQuantity);

        return requestedQuantity - availableForPromotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Stock getStock() {
        return stock;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
