package store.domain;

public record Product(
        String name,
        int price,
        Stock stock,
        Promotion promotion) {
}
