package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.GivenItemDto;
import store.dto.PromotionDetailDto;
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
                .mapToInt(item -> item.getProduct().price() * item.getQuantity())
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

    public List<GivenItemDto> createGivenItems(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .map(this::toGivenItemDto)
                .toList();
    }

    public void updateProductStock(List<PurchaseItem> purchaseItems) {
        purchaseItems.forEach(this::updateEachStock);
    }

    public PromotionDetailDto getPromotionDetail(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();

        if (product.promotion() != null) {
            return product.promotion()
                    .calculatePromotionAndFree(purchaseItem.getQuantity(), product.stock().getPromotionStock());
        }

        return new PromotionDetailDto(0, product.stock().getRegularStock());
    }

    private PurchasedDto toPurchasedDto(PurchaseItem item) {
        String productName = item.getProduct().name();
        int quantity = item.getQuantity();
        int totalPrice = quantity * item.getProduct().price();

        return new PurchasedDto(productName, quantity, totalPrice);
    }

    private GivenItemDto toGivenItemDto(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        int freeQuantity = calculateFreeQuantity(purchaseItem);

        return new GivenItemDto(product.name(), freeQuantity, product.price());
    }

    private int calculateFreeQuantity(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();

        if (product.promotion() != null) {
            return product.promotion().calculateFreeQuantity(product.stock().getPromotionStock());
        }

        return 0;
    }

    private void updateEachStock(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        int purchasedQuantity = purchaseItem.getQuantity();

        PromotionDetailDto promotionDetailDto = getPromotionDetail(purchaseItem);

        int promotionQuantity = Math.min(promotionDetailDto.promotionQuantity(), purchasedQuantity);
        int regularQuantity = purchasedQuantity - promotionQuantity;

        product.stock().updatePromotionStock(promotionQuantity);
        product.stock().updateRegularStock(regularQuantity);
    }
}
