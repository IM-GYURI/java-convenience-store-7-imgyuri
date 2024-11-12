package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.GivenItemDto;
import store.dto.PromotionDetailDto;
import store.dto.PurchasedDto;
import store.dto.ReceiptDto;
import store.util.MembershipCalculator;
import store.util.ProductLoader;
import store.util.PurchaseItemProcessor;

public class StoreService {

    private final ProductLoader productLoader;
    private final PurchaseItemProcessor purchaseItemProcessor;

    public StoreService(ProductLoader productLoader, PurchaseItemProcessor purchaseItemProcessor) {
        this.productLoader = productLoader;
        this.purchaseItemProcessor = purchaseItemProcessor;
    }

    public Products loadProductsFromFile(String productFilePath) {
        return productLoader.loadProducts(productFilePath);
    }

    public List<PurchaseItem> parsePurchaseItems(String inputStatement, Products products) {
        return purchaseItemProcessor.parsePurchaseItems(inputStatement, products);
    }

    public int calculateMembershipDiscount(int currentPrice, boolean isMember) {
        if (isMember) {
            return MembershipCalculator.calculateMembershipDiscount(currentPrice);
        }

        return 0;
    }

    public int calculateTotalPrice(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .mapToInt(item -> item.getProduct().calculateTotalPrice(item.getQuantity()))
                .sum();
    }

    public int calculatePromotionDiscount(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .mapToInt(purchaseItem -> purchaseItem.getProduct()
                        .calculatePromotionDiscount(purchaseItem.getQuantity()))
                .sum();
    }

    public List<PurchasedDto> createPurchasedResults(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .map(PurchasedDto::from)
                .toList();
    }

    public List<GivenItemDto> createGivenItems(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .map(GivenItemDto::from)
                .toList();
    }

    public synchronized void updateProductStock(List<PurchaseItem> purchaseItems) {
        purchaseItems.forEach(purchaseItem -> {
            Product product = purchaseItem.getProduct();
            int purchasedQuantity = purchaseItem.getQuantity();
            product.updateStock(purchasedQuantity);
        });
    }

    public PromotionDetailDto getPromotionDetail(PurchaseItem purchaseItem) {
        return PromotionDetailDto.from(purchaseItem);
    }

    public ReceiptDto generateReceipt(List<PurchaseItem> purchaseItems, boolean isMember) {
        int totalQuantity = purchaseItems.stream()
                .mapToInt(PurchaseItem::getQuantity)
                .sum();
        int totalPrice = calculateTotalPrice(purchaseItems);
        int promotionDiscount = calculatePromotionDiscount(purchaseItems);
        int membershipDiscount = calculateMembershipDiscount(totalPrice - promotionDiscount, isMember);

        return new ReceiptDto(createPurchasedResults(purchaseItems)
                , createGivenItems(purchaseItems), totalQuantity, totalPrice, promotionDiscount,
                membershipDiscount, totalPrice - promotionDiscount - membershipDiscount);
    }
}
