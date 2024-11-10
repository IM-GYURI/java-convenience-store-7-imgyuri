package store.controller;

import static store.exception.ExceptionHandler.getValidInput;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.ReceiptDto;
import store.service.StoreService;
import store.view.View;

public class StoreController {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String YES_ANSWER = "Y";

    private final View view;
    private final StoreService storeService;

    public StoreController(final View view, final StoreService storeService) {
        this.view = view;
        this.storeService = storeService;
    }

    public void enterStore() {
        view.printHello();
        Products products = storeService.loadProductsFromFile(PRODUCT_FILE_PATH);
        view.printCurrentProducts(products);

        List<PurchaseItem> purchaseItems = parsePurchaseItemsFromInput(products);
        processPromotions(purchaseItems);
        ReceiptDto receipt = handleMemberShip(purchaseItems);
    }

    private List<PurchaseItem> parsePurchaseItemsFromInput(Products products) {
        return getValidInput(() ->
                storeService.parsePurchaseItems(view.requestProductSelect(), products));
    }

    private void processPromotions(List<PurchaseItem> purchaseItems) {
        purchaseItems.forEach(purchaseItem -> {
            if (purchaseItem.needsAdditionalPurchase()) {
                handlePromotion(purchaseItem);
            }

            determineNoPromotion(purchaseItem);
        });
    }

    private void determineNoPromotion(PurchaseItem purchaseItem) {
        int shortageQuantity = calculateShortageQuantity(purchaseItem);
        if (shortageQuantity > 0) {
            handleNoPromotion(purchaseItem.getProduct(), shortageQuantity);
        }
    }

    private int calculateShortageQuantity(PurchaseItem purchaseItem) {
        if (purchaseItem.getProduct().getPromotion() != null) {
            return purchaseItem.getQuantity() - purchaseItem.getProduct().getStock().getPromotionStock();
        }

        return 0;
    }

    private void handlePromotion(PurchaseItem purchaseItem) {
        if (isYes(askAdditionalPurchase(purchaseItem.getProduct()))) {
            purchaseItem.buyMore();
        }
    }

    private void handleNoPromotion(Product product, int shortageQuantity) {
        if (!isYes(askOkWithNoPromotion(product, shortageQuantity))) {
            // 처음부터 재시작
        }
    }

    private ReceiptDto handleMemberShip(List<PurchaseItem> purchaseItems) {
        if (isYes(askMembership())) {
            return generateReceipt(purchaseItems, true);
        }

        return generateReceipt(purchaseItems, false);
    }

    private String askAdditionalPurchase(Product product) {
        return getValidInput(() ->
                view.askAdditionalPurchase(product));
    }

    private String askOkWithNoPromotion(Product product, int shortageQuantity) {
        return getValidInput(() ->
                view.askNoPromotion(product, shortageQuantity));
    }

    private String askMembership() {
        return getValidInput(view::askMembership);
    }

    private boolean isYes(String yesOrNo) {
        return YES_ANSWER.equals(yesOrNo);
    }

    private ReceiptDto generateReceipt(List<PurchaseItem> purchaseItems, boolean isMember) {
        int totalPrice = storeService.calculateTotalPrice(purchaseItems);
        int promotionDiscount = storeService.calculatePromotionDiscount(purchaseItems);
        int membershipDiscount = storeService.calculateMembershipDiscount(totalPrice, isMember);

        return new ReceiptDto(storeService.createPurchasedResults(purchaseItems)
                , storeService.createGivenItems(purchaseItems), totalPrice, promotionDiscount, membershipDiscount
                , totalPrice - promotionDiscount - membershipDiscount);
    }
}
