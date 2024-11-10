package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.PurchaseItem;
import store.dto.GivenItemDto;
import store.dto.PurchasedDto;
import store.util.MembershipCalculator;
import store.util.ProductLoader;
import store.util.PromotionCalculator;
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

    public int calculateMembershipDiscount(int totalPrice, boolean isMember) {
        if (isMember) {
            return MembershipCalculator.calculateMembershipDiscount(totalPrice);
        }

        return 0;
    }

    public int calculateTotalPrice(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public int calculatePromotionDiscount(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .mapToInt(PromotionCalculator::calculatePromotionDiscount)
                .sum();
    }

    public List<PurchasedDto> createPurchasedResults(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .map(this::toPurchasedDto)
                .toList();
    }

    private PurchasedDto toPurchasedDto(PurchaseItem item) {
        String productName = item.getProduct().getName();
        int productPrice = item.getProduct().getPrice();
        int totalPrice = productPrice * item.getQuantity();

        return new PurchasedDto(productName, productPrice, totalPrice);
    }

    public List<GivenItemDto> createGivenItems(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .map(this::toGivenItemDto)
                .toList();
    }

    private GivenItemDto toGivenItemDto(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        int freeQuantity = calculateFreeQuantity(purchaseItem);

        return new GivenItemDto(product.getName(), freeQuantity, product.getPrice());
    }

    private int calculateFreeQuantity(PurchaseItem purchaseItem) {
        Promotion promotion = purchaseItem.getProduct().getPromotion();

        if (promotion != null) {
            return promotion.calculateFreeQuantity(purchaseItem.getQuantity());
        }

        return 0;
    }
}
