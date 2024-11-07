package store.controller;

import java.util.List;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.service.StoreService;
import store.view.View;

public class StoreController {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

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

        List<PurchaseItem> purchaseItems = storeService.parsePurchaseItems(view.requestProductSelect(), products);
    }
}
