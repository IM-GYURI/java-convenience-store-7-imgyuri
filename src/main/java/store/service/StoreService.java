package store.service;

import java.util.List;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.util.ProductLoader;
import store.util.PurchaseItemParser;

public class StoreService {

    private final ProductLoader productLoader;
    private final PurchaseItemParser purchaseItemParser;

    public StoreService(ProductLoader productLoader, PurchaseItemParser purchaseItemParser) {
        this.productLoader = productLoader;
        this.purchaseItemParser = purchaseItemParser;
    }

    public Products loadProductsFromFile(String productFilePath) {
        return productLoader.loadProducts(productFilePath);
    }

    public List<PurchaseItem> parsePurchaseItems(String inputStatement, Products products) {
        return purchaseItemParser.parsePurchaseItems(inputStatement, products);
    }
}
