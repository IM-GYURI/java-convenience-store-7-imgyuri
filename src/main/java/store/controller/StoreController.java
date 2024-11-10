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
        purchaseItems.forEach(purchaseItem -> {
            if (!purchaseItem.needsAdditionalPurchase()) {
                Product product = purchaseItem.getProduct();

                int shortageQuantity = product.calculateRegularQuantity(purchaseItem.getQuantity());
                handleNoPromotion(product, shortageQuantity);
                return;
            }
            handlePromotion(purchaseItem);
        });
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

    private String askAdditionalPurchase(Product product) {
        return getValidInput(() ->
                view.askAdditionalPurchase(product));
    }

    private String askOkWithNoPromotion(Product product, int shortageQuantity) {
        return getValidInput(() ->
                view.askNoPromotion(product, shortageQuantity));
    }

    private boolean isYes(String yesOrNo) {
        return YES_ANSWER.equals(yesOrNo);
    }
}
