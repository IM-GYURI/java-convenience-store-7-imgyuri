package store.dto;

import store.domain.PurchaseItem;

public record PurchasedDto(
        String name,
        int quantity,
        int totalPrice) {

    public static PurchasedDto from(PurchaseItem item) {
        String productName = item.getProduct().name();
        int quantity = item.getQuantity();
        int totalPrice = quantity * item.getProduct().price();

        return new PurchasedDto(productName, quantity, totalPrice);
    }
}
