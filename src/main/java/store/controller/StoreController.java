package store.controller;

import static store.exception.ExceptionHandler.getValidInput;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
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

    public void buyProducts() {
        view.printHello();
        Products products = storeService.loadProductsFromFile(PRODUCT_FILE_PATH);
        view.printCurrentProducts(products);

        List<PurchaseItem> purchaseItems = parsePurchaseItemsFromInput(products);
        processPromotions(purchaseItems);
    }

    private List<PurchaseItem> parsePurchaseItemsFromInput(Products products) {
        return getValidInput(() ->
                storeService.parsePurchaseItems(view.requestProductSelect(), products));
    }

    private void processPromotions(List<PurchaseItem> purchaseItems) {
        purchaseItems.stream()
                .filter(PurchaseItem::needsAdditionalPurchase)
                .forEach(this::handlePromotion);
    }

    private void handlePromotion(PurchaseItem purchaseItem) {
        if (isYes(askAdditionalPurchase(purchaseItem.product()))) {
            purchaseItem.buyMore();
        }
    }

    private String askAdditionalPurchase(Product product) {
        return getValidInput(() ->
                view.askAdditionalPurchase(product));
    }

    private boolean isYes(String yesOrNo) {
        return YES_ANSWER.equals(yesOrNo);
    }
}
