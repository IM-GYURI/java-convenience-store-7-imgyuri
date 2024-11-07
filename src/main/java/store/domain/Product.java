package store.domain;

public class Product {

    private String name;
    private int price;
    private int quantity;
    private Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        // 검증 로직 추가

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }


}
