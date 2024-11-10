package store.dto;

import java.util.List;

public record ReceiptDto(
        List<PurchasedDto> purchasedItems,
        List<GivenItemDto> givenItems,
        int totalPrice,
        int promotionDiscountPrice,
        int membershipDiscountPrice,
        int payPrice) {
}
