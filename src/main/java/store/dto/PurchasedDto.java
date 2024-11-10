package store.dto;

public record PurchasedDto(
        String name,
        int quantity,
        int totalPrice) {
}
