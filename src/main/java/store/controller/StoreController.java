package store.controller;

import java.util.List;
import store.domain.Product;
import store.service.StoreService;
import store.view.OutputView;

public class StoreController {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    private final OutputView outputView;
    private final StoreService storeService;

    public StoreController(OutputView outputView, StoreService storeService) {
        this.outputView = outputView;
        this.storeService = storeService;
    }

    public void buyProducts() {
        printStartStatement();
        List<Product> products = storeService.loadProductsFromFile(PRODUCT_FILE_PATH);
        printCurrentProducts(products);
    }

    private void printStartStatement() {
        outputView.printHello();
    }

    private void printCurrentProducts(List<Product> products) {
        outputView.printCurrentProducts(products);
    }
}
