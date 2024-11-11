package store.controller;

import static store.exception.ExceptionHandler.getValidInput;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.PurchaseItem;
import store.dto.PromotionDetailDto;
import store.dto.ReceiptDto;
import store.service.StoreService;
import store.view.View;

public class StoreController {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String YES_ANSWER = "Y";

    private final View view;
    private final StoreService storeService;

    private Products products;

    public StoreController(final View view, final StoreService storeService) {
        this.view = view;
        this.storeService = storeService;
    }

    public void enterStore() {
        this.products = storeService.loadProductsFromFile(PRODUCT_FILE_PATH);
        startPurchaseSession();
    }

    private void startPurchaseSession() {
        printWelcomeAndProducts();
        List<PurchaseItem> purchaseItems = parsePurchaseItemsFromInput();
        processPromotions(purchaseItems);
        completeTransaction(purchaseItems);
        promptPurchaseAgain();
    }

    private void printWelcomeAndProducts() {
        view.printHello();
        view.printCurrentProducts(products);
    }

    private List<PurchaseItem> parsePurchaseItemsFromInput() {
        return getValidInput(() ->
                storeService.parsePurchaseItems(view.requestProductSelect(), products));
    }

    private void processPromotions(List<PurchaseItem> purchaseItems) {
        purchaseItems.forEach(purchaseItem -> {
            if (purchaseItem.needsAdditionalPurchase()) {
                promptAdditionalPurchase(purchaseItem);
            }

            checkAndHandleNoPromotion(purchaseItem);
        });
    }

    private synchronized void completeTransaction(List<PurchaseItem> purchaseItems) {
        ReceiptDto receiptDto = generateReceiptWithMembershipOption(purchaseItems);
        view.printReceipt(receiptDto);
        storeService.updateProductStock(purchaseItems);
    }

    private void promptAdditionalPurchase(PurchaseItem purchaseItem) {
        if (isYes(askAdditionalPurchase(purchaseItem.getProduct()))) {
            purchaseItem.buyMore();
        }
    }

    private void checkAndHandleNoPromotion(PurchaseItem purchaseItem) {
        PromotionDetailDto promotionDetailDto = storeService.getPromotionDetail(purchaseItem);
        Promotion promotion = purchaseItem.getProduct().promotion();
        int remainingQuantity = promotionDetailDto.remainingQuantity();

        if (promotion != null && remainingQuantity > 0) {
            promptNoPromotion(purchaseItem.getProduct(), remainingQuantity);
        }
    }

    private void promptNoPromotion(Product product, int shortageQuantity) {
        if (!isYes(askOkWithNoPromotion(product, shortageQuantity))) {
            startPurchaseSession();
        }
    }

    private void promptPurchaseAgain() {
        if (isYes(askPurchaseAgain())) {
            view.printBlank();
            startPurchaseSession();
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

    private String askPurchaseAgain() {
        return getValidInput(view::askPurchaseAgain);
    }

    private boolean isYes(String yesOrNo) {
        return YES_ANSWER.equals(yesOrNo);
    }

    private ReceiptDto generateReceiptWithMembershipOption(List<PurchaseItem> purchaseItems) {
        boolean isMember = isYes(getValidInput(view::askMembership));
        return storeService.generateReceipt(purchaseItems, isMember);
    }
}
