package store.service;

import java.util.List;
import store.domain.Products;
import store.domain.PurchaseItem;
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
}
